package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.ShopDto;
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

        List<OrderDto> ordersDtos = Converter.toOrdersDto(orders);

        return new ResponseEntity<>(ordersDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/addresses", method = RequestMethod.GET)
    public ResponseEntity<?> getAddresses(@PathVariable("id") long userId) {
        List<Address> addresses = addressService.findByUserId(userId);

        if (addresses.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        List<AddressDto> addressesDtos = Converter.toAddressesDto(addresses);

        return new ResponseEntity<>(addressesDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/shops", method = RequestMethod.GET)
    public ResponseEntity<?> getShops(@PathVariable("id") long userId) {
        List<Shop> shops = shopService.findByUserId(userId);

        if (shops.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        List<ShopDto> shopsDtos = Converter.toShopsDto(shops);

        return new ResponseEntity<>(shopsDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{user_id}/shops/{shop_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShop(@PathVariable("user_id") long userId,
                                        @PathVariable("shop_id") long shopId) {
        Shop shop = shopService.findOne(shopId);

        if (shop == null) {
            return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
        }

        shopService.delete(shopId);
        return new ResponseEntity<>(shopId, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/addresses", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAddress(@PathVariable("id") long userId, @RequestBody AddressDto addressDto) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Address existingAddress = addressService.findByNameAndUser(addressDto.getName(), user);

        if (existingAddress != null) {
            return new ResponseEntity<>(new ErrorResponseDto("internal", "Address with this name is already exists"),
                    HttpStatus.BAD_REQUEST);
        } else {
            Address address = Converter.toAddressEntity(addressDto);
            address.setUser(user);
            user.getAddresses().add(address);

            userService.saveOrUpdate(user);
            return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{user_id}/addresses/{address_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@PathVariable("user_id") long userId,
                                           @PathVariable("address_id") long addressId) {
        Address address = addressService.findOne(addressId);

        if (address == null) {
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }

        addressService.delete(addressId);
        return new ResponseEntity<>(addressId, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@PathVariable("id") long userId, @RequestBody OrderDto orderDto) {
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
        order.setCourier(user); // FIXME
        orderService.saveOrUpdate(order);

        user.getOrders().add(order);
        userService.saveOrUpdate(user);

        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
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
