package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.repository.OrderRepository;
import com.github.handioq.diber.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order getById(long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public Page<Order> findAllByPage(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Page<Order> findByUserId(long userId, Pageable pageable, Specification<Order> spec) {
        return orderRepository.findByUserId(userId, pageable, spec);
    }

    @Override
    public List<Order> findAll(Specification<Order> spec) {
        return orderRepository.findAll(spec);
    }

    @Override
    public void saveOrUpdate(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void delete(long orderId) {
        orderRepository.delete(orderId);
    }

    @Override
    public Long count() {
        return orderRepository.count();
    }
}