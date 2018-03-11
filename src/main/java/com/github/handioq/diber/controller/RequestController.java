package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.RequestService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_REQUESTS)
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public RequestController(RequestService requestService, OrderService orderService, UserService userService) {
        this.requestService = requestService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRequestById(@PathVariable("id") long id) {
        LOGGER.info("Start getRequestById id: {}", id);
        Request request = requestService.getById(id);

        if (request == null) {
            LOGGER.error("Request with id {} is not found", id);
            return new ResponseEntity<>("Request not found", HttpStatus.NOT_FOUND);
        }

        RequestDto requestDto = RequestDto.toDto(request);
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{order_id}/user/{user_id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRequestByOrderAndUser(@PathVariable("order_id") long orderId,
                                                      @PathVariable("user_id") long userId) {
        LOGGER.info("Start getRequestByOrderAndUser");
        Request request = requestService.findByOrderIdAndCourierId(orderId, userId);

        if (request == null) {
            LOGGER.error("Request {} is not found");
            return new ResponseEntity<>("Request not found", HttpStatus.NOT_FOUND);
        }

        RequestDto requestDto = RequestDto.toDto(request);
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeStatus(@PathVariable("id") long id, @RequestBody RequestDto requestDto) {
        LOGGER.info("Start changeStatus id: {}", id);
        Request request = requestService.getById(id);

        if (request == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Request not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (requestDto.getStatus() == null || requestDto.getStatus().isEmpty()) {
            LOGGER.error("requestDto status is null or empty");
            ErrorResponseDto error = new ErrorResponseDto("Empty", "Status is empty");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        request.setStatus(requestDto.getStatus());
        requestService.saveOrUpdate(request);
        LOGGER.info("request status changed to {}, request saved to database", requestDto.getStatus());

        if (requestDto.getStatus().equalsIgnoreCase("Accepted")) {
            // find the corresponding order and set the request courier
            Order order = orderService.getById(request.getOrder().getId());
            User courier = userService.findOne(request.getCourier().getId());
            order.setStatus("Accepted"); // accepted by the customer and ready for performing
            order.setCourier(courier);
            orderService.saveOrUpdate(order);
        } else if (requestDto.getStatus().equalsIgnoreCase("Canceled by courier")
                || requestDto.getStatus().equalsIgnoreCase("Canceled by customer"))  {
            Order order = orderService.getById(request.getOrder().getId());
            order.setStatus("New"); // request was canceled by customer so set "New" status
            order.setCourier(null);
            orderService.saveOrUpdate(order);
        }

        return new ResponseEntity<>(RequestDto.toDto(request), HttpStatus.OK);
    }

}