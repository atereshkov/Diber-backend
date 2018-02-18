package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrderService {

    Order getById(long id);

    Page<Order> findAllByPage(Pageable pageable);

    List<Order> findAll(Specification<Order> spec);

    Page<Order> findByUserId(long userId, Pageable pageable, Specification<Order> spec);

    void saveOrUpdate(Order order);

    void delete(long orderId);

    Long count();

}