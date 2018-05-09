package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Invoice;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDto extends BaseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("paid")
    private Double paid;

    @JsonProperty("service_tax")
    private Double tax;

    @JsonProperty("customer")
    private UserDto customer;

    @JsonProperty("courier")
    private UserDto courier;

    @JsonProperty("order")
    private OrderDto order;

    public InvoiceDto() {
    }

    public InvoiceDto(long id, String status, Double total, Double paid, Double tax, UserDto customer, UserDto courier, OrderDto order) {
        this.id = id;
        this.status = status;
        this.total = total;
        this.paid = paid;
        this.tax = tax;
        this.customer = customer;
        this.courier = courier;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserDto getCustomer() {
        return customer;
    }

    public void setCustomer(UserDto customer) {
        this.customer = customer;
    }

    public UserDto getCourier() {
        return courier;
    }

    public void setCourier(UserDto courier) {
        this.courier = courier;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public static InvoiceDto toDto(Invoice invoice) {
        UserDto customer = UserDto.toDto(invoice.getUser());
        UserDto courier = UserDto.toDto(invoice.getCourier());
        OrderDto order = OrderDto.toDto(invoice.getOrder());
        return new InvoiceDto(invoice.getId(), invoice.getStatus(), invoice.getTotal(),
                invoice.getPaid(), invoice.getTax(), customer, courier, order);
    }

    public static List<InvoiceDto> toDto(List<Invoice> invoices) {
        List<InvoiceDto> invoiceDtos = new ArrayList<>();

        for (Invoice invoice : invoices) {
            invoiceDtos.add(InvoiceDto.toDto(invoice));
        }

        return invoiceDtos;
    }

}
