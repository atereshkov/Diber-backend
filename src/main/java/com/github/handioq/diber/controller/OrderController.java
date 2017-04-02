package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.RequestService;
import com.github.handioq.diber.utils.Constants;
import com.github.handioq.diber.utils.Converter;
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

    @Autowired
    OrderService orderService;

    @Autowired
    RequestService requestService;

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
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }

        Request request = Converter.toRequestEntity(requestDto);
        order.getRequests().add(request);
        orderService.saveOrUpdate(order);

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        Order order = Converter.toOrderEntity(orderDto);

        orderService.saveOrUpdate(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

}
