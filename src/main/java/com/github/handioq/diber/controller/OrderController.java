package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.repository.specification.OrderSpecificationsBuilder;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.RequestService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_ORDERS)
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final RequestService requestService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, RequestService requestService, UserService userService) {
        this.orderService = orderService;
        this.requestService = requestService;
        this.userService = userService;
    }

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
    public ResponseEntity<?> getOrders(@RequestParam(value = "search", required = false) String search,
                                       Pageable pageable) {
        // TODO: 24.02.2018 Refactor and optimize this shit

        if (search == null) {
            Page<Order> orders = orderService.findAllByPage(pageable);
            Page<OrderDto> ordersDtos = orders.map(OrderDto::toDto);
            return new ResponseEntity<>(ordersDtos, HttpStatus.OK);
        } else {
            OrderSpecificationsBuilder builder = new OrderSpecificationsBuilder();
            Pattern patternSimple = Pattern.compile("(\\w+?)(:|<|>|!)([\\w ]+)");
            Pattern patternComplex = Pattern.compile("(\\w+?)[.]?(\\w+?)(:|<|>|!)([\\w ]+)");

            String[] searchQueries = search.split(",");

            for (String query : searchQueries) {
                LOGGER.info("Search for {}", query);
                if (query.contains(".")) {
                    Matcher matcher = patternComplex.matcher(query);
                    matcher.find();
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
                } else {
                    Matcher matcher = patternSimple.matcher(query);
                    matcher.find();
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
                }
            }

            Specification<Order> spec = builder.build();
            Page<Order> orders = orderService.findAll(spec, pageable);
            Page<OrderDto> ordersDtos = orders.map(OrderDto::toDto);
            return new ResponseEntity<>(ordersDtos, HttpStatus.OK);
        }
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
            switch (existingRequest.getStatus()) {
                case "Not reviewed": {
                    ErrorResponseDto error = new ErrorResponseDto("Already exists", "The request already exists");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
                case "Canceled by courier":
                    existingRequest.setStatus("Not reviewed");
                    requestService.saveOrUpdate(existingRequest);
                    return new ResponseEntity<>(requestDto, HttpStatus.OK);
                case "Canceled by customer": {
                    ErrorResponseDto error = new ErrorResponseDto("Request rejected", "The request was rejected earlier by the customer");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
            }
        }

        Request request = new Request(order, courier, "Not reviewed");

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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> editOrder(@PathVariable("id") long id, @RequestBody OrderDto orderDto) {
        LOGGER.info("Start edit order with id: {}", id);
        Order order = orderService.getById(id);

        if (order == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Order not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        order.setPrice(order.getPrice());
        order.setDescription(order.getDescription());
        order.setAddressTo(order.getAddressTo());
        order.setAddressFrom(order.getAddressFrom());
        order.setStatus(orderDto.getStatus());
        orderService.saveOrUpdate(order);
        LOGGER.info("Order updated successfully");
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
