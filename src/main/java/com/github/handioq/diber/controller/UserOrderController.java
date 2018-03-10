package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_ORDER)
public class UserOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderController.class);

    private final OrderService orderService;
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserOrderController(OrderService orderService, UserService userService, AddressService addressService) {
        this.orderService = orderService;
        this.userService = userService;
        this.addressService = addressService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal User userPrincipal,
                                       @PathVariable("user_id") long userId, Pageable pageable) {
        LOGGER.info("getOrders for userId: {}", userId);
        Page<Order> orders = orderService.findByUserId(userId, pageable);
        Page<OrderDto> ordersDtos = orders.map(OrderDto::toDto);
        return new ResponseEntity<>(ordersDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@AuthenticationPrincipal User userPrincipal,
                                      @PathVariable("user_id") long userId,
                                      @RequestBody OrderDto orderDto) {
        LOGGER.info("Starting of addOrder");

        User user = userService.findOne(userId);

        if (user == null) {
            LOGGER.error("User with id {} is not found", userId);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // TODO refactor

        Address addressTo = Address.toEntity(orderDto.getAddressTo());
        Address existingAddressTo = addressService.findByNameAndUser(addressTo.getName(), user);

        // if address already exists, then don't create new entity in database
        if (existingAddressTo != null) {
            LOGGER.info("AddressTo from is already exists: {}", existingAddressTo);
            addressTo = existingAddressTo;
        } else {
            LOGGER.info("Create new AddressTo entity for order");
            addressTo.setUser(user);
            addressService.saveOrUpdate(addressTo);
            user.getAddresses().add(addressTo);
        }

        Address addressFrom = Address.toEntity(orderDto.getAddressFrom());
        Address existingAddressFrom = addressService.findByNameAndUser(addressFrom.getName(), user);

        // if address already exists, then don't create new entity in database
        if (existingAddressFrom != null) {
            LOGGER.info("AddressFrom is already exists: {}", existingAddressFrom);
            addressFrom = existingAddressFrom;
        } else {
            LOGGER.info("Create new AddressFrom entity for order");
            addressFrom.setUser(user);
            addressService.saveOrUpdate(addressFrom);
            user.getAddresses().add(addressFrom);
        }

        Order order = Order.toEntity(orderDto);
        order.setAddressTo(addressTo);
        order.setAddressFrom(addressFrom);
        order.setUser(user);
        order.setCourier(null); // add order without courier as initial
        orderService.saveOrUpdate(order);

        user.getOrders().add(order);
        userService.saveOrUpdate(user);
        LOGGER.info("New order was saved to database");
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
