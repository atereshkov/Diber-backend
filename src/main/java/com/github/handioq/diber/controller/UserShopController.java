package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.ShopDto;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Shop;
import com.github.handioq.diber.model.entity.User;
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
@RequestMapping(Constants.API_URL + Constants.URL_USER_SHOP)
public class UserShopController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserShopController.class);

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getShops(@AuthenticationPrincipal User userPrincipal,
                                      @PathVariable("user_id") long userId) {
        List<Shop> shops = shopService.findByUserId(userId);

        if (shops.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        List<ShopDto> shopsDtos = Converter.toShopsDto(shops);

        return new ResponseEntity<>(shopsDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addShop(@AuthenticationPrincipal User userPrincipal,
                                     @PathVariable("user_id") long userId, @RequestBody ShopDto shopDto) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Shop existingShop = shopService.findByNameAndUser(shopDto.getName(), user);

        if (existingShop != null) {
            ErrorResponseDto error = new ErrorResponseDto("internal",
                    "Shop with this name is already exists");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } else {
            Shop shop = Converter.toShopEntity(shopDto);
            shop.setUser(user);
            user.getShops().add(shop);

            userService.saveOrUpdate(user);
            return new ResponseEntity<>(shopDto, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{shop_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShop(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId,
                                        @PathVariable("shop_id") long shopId) {
        Shop shop = shopService.findOne(shopId);

        if (shop == null) {
            return new ResponseEntity<>("Shop not found", HttpStatus.NOT_FOUND);
        }

        // todo add check for order status and if one of the orders has "In progress" status then don't delete shop

        for (Order order : shop.getOrders()) {
            order.setShop(null);
        }

        shopService.delete(shopId);
        return new ResponseEntity<>(shopId, HttpStatus.NO_CONTENT);
    }

}
