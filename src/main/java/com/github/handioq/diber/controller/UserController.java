package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.entity.*;
import com.github.handioq.diber.service.*;
import com.github.handioq.diber.utils.Constants;
import com.github.handioq.diber.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USERS)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        User user = userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBytId(@PathVariable("id") long id) {
        User user = userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getReviews(@PathVariable("id") long userId) {
        List<Review> reviews = reviewService.findByUserId(userId);

        if (reviews.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value = "/{user_id}/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@PathVariable("user_id") long userId) {
        List<Order> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/addresses", method = RequestMethod.GET)
    public ResponseEntity<?> getAddresses(@PathVariable("id") long userId) {
        List<Address> addresses = addressService.findByUserId(userId);

        if (addresses.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/shops", method = RequestMethod.GET)
    public ResponseEntity<?> getShops(@PathVariable("id") long userId) {
        List<Shop> shops = shopService.findByUserId(userId);

        if (shops.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/addresses", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAddress(@PathVariable("id") long userId, @RequestBody AddressDto addressDto) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Address> userAddresses = addressService.findByUserId(userId);
        boolean addressAlreadyExists = false;

        for (Address address : userAddresses) {
            if (address.getName().equalsIgnoreCase(addressDto.getName())) {
                addressAlreadyExists = true;
                break;
            }
        }

        if(!addressAlreadyExists) {
            Address address = Converter.toAddressEntity(addressDto);
            address.setUser(user);
            user.getAddresses().add(address);

            userService.saveOrUpdate(user);
            return new ResponseEntity<>(address, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponseDto("internal", "Address already exists"),
                    HttpStatus.BAD_REQUEST);
        }


    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@PathVariable("id") long userId, @RequestBody OrderDto orderDto) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // TODO add all checks for duplicates of shops and addresses

        Shop shop = Converter.toShopEntity(orderDto.getShop());
        shop.setUser(user);
        shopService.saveOrUpdate(shop);

        Address address = Converter.toAddressEntity(orderDto.getAddress());
        address.setUser(user);
        addressService.saveOrUpdate(address);

        Order order = Converter.toOrderEntity(orderDto);
        order.setShop(shop);
        order.setAddress(address);
        order.setUser(user);
        order.setCourier(user); // FIXME
        orderService.saveOrUpdate(order);

        user.getOrders().add(order);
        user.getAddresses().add(address);
        user.getShops().add(shop);
        userService.saveOrUpdate(user);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getById(@AuthenticationPrincipal User user) {
        //User foundUser = userService.findOne(user.getId());

        //if (foundUser == null) {
        //    return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        //}

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
