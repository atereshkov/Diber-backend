package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDto {

    @JsonProperty("order")
    private OrderDto order;

    @JsonProperty("courier")
    private UserDto courier;

    public RequestDto() {
    }

    public RequestDto(OrderDto order, UserDto courier) {
        this.order = order;
        this.courier = courier;
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