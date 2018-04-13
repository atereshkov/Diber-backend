package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.AuditableEntity;
import com.github.handioq.diber.model.dto.RequestDto;

import javax.persistence.*;

@Entity
@Table(name = "requests")
public class Request extends AuditableEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    public Request() {
    }

    public Request(Order order, User courier, String status) {
        this.order = order;
        this.courier = courier;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "Request{" +
                "order=" + order +
                ", courier=" + courier +
                '}';
    }

    public static Request toEntity(RequestDto requestDto) {
        return new Request(); // todo implement
    }

}
