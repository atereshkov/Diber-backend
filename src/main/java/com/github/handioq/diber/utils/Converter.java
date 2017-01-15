package com.github.handioq.diber.utils;

import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.UserDTO;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.User;

public class Converter {

    public static User toUserEntity(UserDTO userDto) {
        return new User(userDto.getEmail(), userDto.getUsername(),
                userDto.getPassword(), userDto.isEnabled());
    }

    public static Order toOrderEntity(OrderDto orderDto) {
        return new Order();
    }
}
