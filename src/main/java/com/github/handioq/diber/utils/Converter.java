package com.github.handioq.diber.utils;

import com.github.handioq.diber.model.dto.*;
import com.github.handioq.diber.model.entity.*;

import java.util.Date;

public class Converter {

    public static User toUserEntity(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getUsername(),
                userDto.getPassword(), userDto.isEnabled(), userDto.getFullname());
        // todo complete this
    }

    public static Order toOrderEntity(OrderDto orderDto) {
        //Shop shop = toShopEntity(orderDto.getShop());
        Date date = DateUtil.getFromString(orderDto.getDate(), "yyyy-MM-dd HH:mm:ss");

        return new Order(date, orderDto.getDescription(), orderDto.getPrice(), orderDto.getStatus(), null, null);
    }

    public static Request toRequestEntity(RequestDto requestDto) {
        return new Request(); // todo implement
    }

    public static Address toAddressEntity(AddressDto addressDto) {
        return new Address(addressDto.getName(), addressDto.getPostalCode(), addressDto.getCountry(),
                addressDto.getCity(), addressDto.getRegion(), addressDto.getAddress(),
                addressDto.getPhone());
    }

    public static Shop toShopEntity(ShopDto shopDto) {
        return new Shop(shopDto.getName(), shopDto.getAddress());
    }

}