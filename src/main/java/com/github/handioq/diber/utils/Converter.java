package com.github.handioq.diber.utils;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.dto.UserDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.model.entity.User;

public class Converter {

    public static User toUserEntity(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getUsername(),
                userDto.getPassword(), userDto.isEnabled());
        // todo complete this
    }

    public static Order toOrderEntity(OrderDto orderDto) {
        return new Order(); // todo implement
    }

    public static Request toRequestEntity(RequestDto requestDto) {
        return new Request(); // todo implement
    }

    public static Address toAddressEntity(AddressDto addressDto) {
        return new Address(addressDto.getName(), addressDto.getPostalCode(), addressDto.getCountry(),
                addressDto.getCity(), addressDto.getRegion(), addressDto.getAddress(),
                addressDto.getPhone());
    }

}