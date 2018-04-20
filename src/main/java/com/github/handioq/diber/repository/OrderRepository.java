package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

    Page<Order> findByUserId(long id, Pageable pageable);

    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    List<Order> findAll(Specification<Order> spec);

    Long countByUserId(long userId);

    Long countByUserIdAndStatus(long userId, String status);

}