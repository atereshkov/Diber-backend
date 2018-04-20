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

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    Page<Order> findByUserId(long userId, Pageable pageable);

    void saveOrUpdate(Order order);

    void delete(long orderId);

    Long count();

    Long countByUserId(long userId);

    Long countByUserIdAndStatus(long userId, String status);

}