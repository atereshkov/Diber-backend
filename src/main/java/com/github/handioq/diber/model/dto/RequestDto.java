package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("order")
    private OrderDto order;

    @JsonProperty("courier")
    private UserDto courier;

    public RequestDto() {
    }

    public RequestDto(long id, OrderDto order, UserDto courier) {
        this.id = id;
        this.order = order;
        this.courier = courier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public UserDto getCourier() {
        return courier;
    }

    public void setCourier(UserDto courier) {
        this.courier = courier;
    }
}