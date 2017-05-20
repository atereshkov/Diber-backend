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
import com.github.handioq.diber.utils.Converter;
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

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private AddressService addressService;

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@AuthenticationPrincipal User userPrincipal,
                                       @PathVariable("user_id") long userId) {
        List<Order> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NO_CONTENT);
        }

        List<OrderDto> ordersDtos = Converter.toOrdersDto(orders);

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
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Shop shop = Converter.toShopEntity(orderDto.getShop());
        Shop existingShop = shopService.findByNameAndUser(shop.getName(), user);

        // if shop already exists, then we don't create new shop entity in database
        if (existingShop != null) {
            shop = existingShop;
        } else {
            shop.setUser(user);
            shopService.saveOrUpdate(shop);
            user.getShops().add(shop);
        }

        Address address = Converter.toAddressEntity(orderDto.getAddress());
        Address existingAddress = addressService.findByNameAndUser(address.getName(), user);

        // the same: if address already exists, then don't create new entity in database
        if (existingAddress != null) {
            address = existingAddress;
        } else {
            address.setUser(user);
            addressService.saveOrUpdate(address);
            user.getAddresses().add(address);
        }

        Order order = Converter.toOrderEntity(orderDto);
        order.setShop(shop);
        order.setAddress(address);
        order.setUser(user);
        order.setCourier(null); // add order without courier as initial
        orderService.saveOrUpdate(order);

        user.getOrders().add(order);
        userService.saveOrUpdate(user);

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

}
