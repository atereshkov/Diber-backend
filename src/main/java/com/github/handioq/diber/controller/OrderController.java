package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.RequestService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import com.github.handioq.diber.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_ORDERS)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
        Order order = orderService.getById(id);

        if (order == null) {
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        OrderDto orderDto = Converter.toOrderDto(order);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(Pageable pageable) {
        Page<Order> orderPage = orderService.findAllByPage(pageable);

        return new ResponseEntity<>(orderPage, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/requests", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrderRequests(@PathVariable("id") long id) {
        List<Request> requests = requestService.findByOrderId(id);

        if (requests.isEmpty()) {
            return new ResponseEntity<>("Requests are empty", HttpStatus.NOT_FOUND);
        }

        List<RequestDto> requestsDtos = Converter.toRequestsDto(requests);

        return new ResponseEntity<>(requestsDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/requests", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrderRequest(@PathVariable("id") long id, @RequestBody RequestDto requestDto) {
        Order order = orderService.getById(id);

        if (order == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Order not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        User courier = userService.findOne(requestDto.getCourier().getId());

        if (courier == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Courier not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Request existingRequest = requestService.findByOrderIdAndCourierId(id, requestDto.getCourier().getId());

        if (existingRequest != null) {
            ErrorResponseDto error = new ErrorResponseDto("Already exists", "Request with this order id and courier id already exists.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Request request = new Request(order, courier);

        order.getRequests().add(request);
        orderService.saveOrUpdate(order);

        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> changeStatus(@PathVariable("id") long id, @RequestBody OrderDto orderDto) {
        Order order = orderService.getById(id);

        if (order == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Order not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (orderDto.getStatus() == null || orderDto.getStatus().isEmpty()) {
            ErrorResponseDto error = new ErrorResponseDto("Empty", "Status is empty");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        order.setStatus(orderDto.getStatus());
        orderService.saveOrUpdate(order);

        return new ResponseEntity<>(Converter.toOrderDto(order),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        Order order = Converter.toOrderEntity(orderDto);

        orderService.saveOrUpdate(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
