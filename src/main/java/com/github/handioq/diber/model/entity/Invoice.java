package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.AuditableEntity;
import com.github.handioq.diber.model.dto.InvoiceDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invoices")
public class Invoice extends AuditableEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "total")
    private Double total;

    @NotNull
    @Column(name = "paid")
    private Double paid;

    @NotNull
    @Column(name = "service_tax")
    private Double tax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public Invoice(String status, Double total, Double paid, Double tax, User user, User courier, Order order) {
        this.status = status;
        this.total = total;
        this.paid = paid;
        this.tax = tax;
        this.user = user;
        this.courier = courier;
        this.order = order;
    }

    public Invoice() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // TODO Complete
    public static Invoice toEntity(InvoiceDto invoiceDto) {
        return new Invoice();
    }

}