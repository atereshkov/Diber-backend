package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.UserDto;
import com.github.handioq.diber.model.entity.Role;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AuthService;
import com.github.handioq.diber.service.RoleService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_AUTH)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final RoleService roleService;

    @Autowired
    public AuthController(AuthService authService, RoleService roleService) {
        this.authService = authService;
        this.roleService = roleService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        LOGGER.info("Start register user: {}", userDto);
        User user = User.toEntity(userDto);

        List<Role> roles = new ArrayList<>();

        if (userDto.isCustomer()) {
            LOGGER.info("Register user with ROLE_CUSTOMER");
            roles.add(roleService.findRole(1));
        }
        if (userDto.isCourier()) {
            LOGGER.info("Register user with ROLE_COURIER");
            roles.add(roleService.findRole(2));
        }

        if (!userDto.isCourier() && !userDto.isCustomer()) {
            roles.add(roleService.findRole(1));
            roles.add(roleService.findRole(2));
        }

        user.setRoles(roles);
        user.setEnabled(true); // enable user by default (TODO email-logic)

        authService.register(user);
        return new ResponseEntity<>(UserDto.toDto(user), HttpStatus.CREATED);
    }

}
