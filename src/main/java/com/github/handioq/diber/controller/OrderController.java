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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_ORDERS)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrderById(@PathVariable("id") long id) {
        LOGGER.info("Start getOrderById with id: {}", id);
        Order order = orderService.getById(id);

        if (order == null) {
            LOGGER.info("order with id: {} is NULL", id);
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        OrderDto orderDto = OrderDto.toDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrders(Pageable pageable) {
        List<Order> orders = orderService.findAllByPage(pageable);
        List<OrderDto> ordersDtos = OrderDto.toDto(orders);
        return new ResponseEntity<>(ordersDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/requests", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getOrderRequests(@PathVariable("id") long id) {
        LOGGER.info("Start getOrderRequests with id: {}", id);
        List<Request> requests = requestService.findByOrderId(id);
        List<RequestDto> requestsDtos = RequestDto.toDto(requests);
        return new ResponseEntity<>(requestsDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/requests", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrderRequest(@PathVariable("id") long id, @RequestBody RequestDto requestDto) {
        LOGGER.info("Start addOrderRequest");
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
        LOGGER.info("Start changeStatus with id: {}", id);
        Order order = orderService.getById(id);

        if (order == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Order not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (orderDto.getStatus() == null || orderDto.getStatus().isEmpty()) {
            LOGGER.info("orderDto status is null or empty");
            ErrorResponseDto error = new ErrorResponseDto("Empty", "Status is empty");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        order.setStatus(orderDto.getStatus());
        orderService.saveOrUpdate(order);
        LOGGER.info("order status changed to " + orderDto.getStatus() + ", order savedOrUpdated successfully");
        return new ResponseEntity<>(OrderDto.toDto(order), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        LOGGER.info("Start addOrder: {}", orderDto);
        Order order = Order.toEntity(orderDto);
        orderService.saveOrUpdate(order);
        return new ResponseEntity<>(OrderDto.toDto(order), HttpStatus.CREATED);
    }

    @PreAuthorize("@securityServiceImpl.hasAdminPermissions(#userPrincipal)")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@AuthenticationPrincipal User userPrincipal,
                                         @PathVariable("id") long id) {
        LOGGER.info("Start deleteOrder");
        Order order = orderService.getById(id);

        if (order == null) {
            LOGGER.error("Order with id {} is not found", id);
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        for (Request request : order.getRequests()) {
            LOGGER.info("set order to null of request with id: {}", request.getId());
            request.setOrder(null);
        }

        orderService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

}
