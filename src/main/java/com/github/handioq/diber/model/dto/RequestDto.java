package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestDto extends BaseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("order")
    private OrderDto order;

    @JsonProperty("courier")
    private UserDto courier;

    public RequestDto() {
    }

    public RequestDto(long id, OrderDto order, UserDto courier, String status) {
        this.id = id;
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

    public static RequestDto toDto(Request request) {
        return new RequestDto(request.getId(), OrderDto.toDto(request.getOrder()),
                UserDto.toDto(request.getCourier()), request.getStatus());
    }

    public static List<RequestDto> toDto(List<Request> requests) {
        List<RequestDto> requestsDtos = new ArrayList<>();

        for (Request request : requests) {
            requestsDtos.add(RequestDto.toDto(request));
        }

        return requestsDtos;
    }

}