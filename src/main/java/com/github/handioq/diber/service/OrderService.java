package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getById(long id);

    Page<Order> findAllByPage(Pageable pageable);

    Page<Order> findByUserId(long userId, Pageable pageable);

    void saveOrUpdate(Order order);

    void delete(long orderId);

    Long count();

}