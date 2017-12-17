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

    @JsonProperty("creation_date")
    private long date;

    public RequestDto() {
    }

    public RequestDto(long id, OrderDto order, UserDto courier, String status, long date) {
        this.id = id;
        this.order = order;
        this.courier = courier;
        this.status = status;
        this.date = date;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static RequestDto toDto(Request request) {
        long date = request.getCreatedDate().getTime() / 1000;
        return new RequestDto(request.getId(), OrderDto.toDto(request.getOrder()),
                UserDto.toDto(request.getCourier()), request.getStatus(), date);
    }

    public static List<RequestDto> toDto(List<Request> requests) {
        List<RequestDto> requestsDtos = new ArrayList<>();

        for (Request request : requests) {
            requestsDtos.add(RequestDto.toDto(request));
        }

        return requestsDtos;
    }

}