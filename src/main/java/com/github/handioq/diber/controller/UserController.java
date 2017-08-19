package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.UserDto;
import com.github.handioq.diber.model.entity.User;
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
@RequestMapping(Constants.API_URL + Constants.URL_USERS)
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #id)")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getById(@AuthenticationPrincipal User userPrincipal,
                                     @PathVariable("id") long id) {
        LOGGER.info("Start getById id: {}", id);
        User user = userService.findOne(id);

        if (user == null) {
            LOGGER.error("User with id {} is not found", id);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserDto.toDto(user), HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasAdminPermissions(#userPrincipal)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal User userPrincipal) {
        LOGGER.info("Start getUsers");
        List<User> users = userService.findAll();
        return new ResponseEntity<>(UserDto.toDto(users), HttpStatus.OK); // TODO pagination
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getById(@AuthenticationPrincipal User user) {
        LOGGER.info("Start getById with AuthenticationPrincipal: {}", user);
        return new ResponseEntity<>(UserDto.toDto(user), HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasAdminPermissions(#userPrincipal)")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("id") long id) {
        LOGGER.info("Start deleteById id: {}", id);
        User user = userService.findOne(id);

        if (user == null) {
            LOGGER.error("User with id {} is not found", id);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
