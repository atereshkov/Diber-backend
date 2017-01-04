package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_ORDERS)
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getProductById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);

        if (order == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(Pageable pageable) {
        Page<Order> productPage = orderService.findAllByPage(pageable);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

}
