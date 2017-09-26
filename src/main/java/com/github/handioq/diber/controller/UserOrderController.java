package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.ShopService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_ORDER)
public class UserOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOrderController.class);

    private final OrderService orderService;
    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;

    @Autowired
    public UserOrderController(OrderService orderService, UserService userService, ShopService shopService, AddressService addressService) {
        this.orderService = orderService;
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal User userPrincipal,
                                       @PathVariable("user_id") long userId) {
        LOGGER.info("getOrders for userId: {}", userId);
        List<Order> orders = orderService.findByUserId(userId);
        List<OrderDto> ordersDtos = OrderDto.toDto(orders);
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

        Shop shop = Shop.toEntity(orderDto.getShop());
        Shop existingShop = shopService.findByNameAndUser(shop.getName(), user);

        // if shop already exists, then we don't create new shop entity in database
        if (existingShop != null) {
            LOGGER.info("shop is already exists: {}", existingShop);
            shop = existingShop;
        } else {
            LOGGER.info("create new shop entity for order");
            shop.setUser(user);
            shopService.saveOrUpdate(shop);
            user.getShops().add(shop);
        }

        Address address = Address.toEntity(orderDto.getAddress());
        Address existingAddress = addressService.findByNameAndUser(address.getName(), user);

        // the same: if address already exists, then don't create new entity in database
        if (existingAddress != null) {
            LOGGER.info("address is already exists: {}", existingAddress);
            address = existingAddress;
        } else {
            LOGGER.info("create new address entity for order");
            address.setUser(user);
            addressService.saveOrUpdate(address);
            user.getAddresses().add(address);
        }

        Order order = Order.toEntity(orderDto);
        order.setShop(shop);
        order.setAddress(address);
        order.setUser(user);
        order.setCourier(null); // add order without courier as initial
        orderService.saveOrUpdate(order);

        user.getOrders().add(order);
        userService.saveOrUpdate(user);
        LOGGER.info("New order was saved to database");
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
