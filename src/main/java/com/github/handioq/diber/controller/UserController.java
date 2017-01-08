package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Review;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.ReviewService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(@PathVariable("id") long userId) {
        List<Order> orders = orderService.findByUserId(userId);

        if (orders.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
