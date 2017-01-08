package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getById(long id);

    //List<Order> findAll();

    Page<Order> findAllByPage(Pageable pageable);

    List<Order> findByUserId(long userId);

}