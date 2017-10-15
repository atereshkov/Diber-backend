package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto extends BaseDto {

    private long id;
    private long date;
    private String description;
    private Double price;
    private String status;
    private AddressDto addressFrom;
    private AddressDto addressTo;
    private UserDto courier;
    private UserDto customer;
    // todo add array of requestDto

    public OrderDto() {
    }

    public OrderDto(long id, long date, String description, Double price, String status, AddressDto addressFrom, AddressDto addressTo) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDto getCourier() {
        return courier;
    }

    public void setCourier(UserDto courier) {
        this.courier = courier;
    }

    public UserDto getCustomer() {
        return customer;
    }

    public void setCustomer(UserDto customer) {
        this.customer = customer;
    }

    public AddressDto getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(AddressDto addressFrom) {
        this.addressFrom = addressFrom;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AddressDto getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(AddressDto addressTo) {
        this.addressTo = addressTo;
    }

    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();

        if (order.getAddressFrom() != null) {
            orderDto.setAddressFrom(AddressDto.toDto(order.getAddressFrom()));
        }
        if (order.getAddressTo() != null) {
            orderDto.setAddressTo(AddressDto.toDto(order.getAddressTo()));
        }
        if (order.getCourier() != null) {
            orderDto.setCourier(UserDto.toDto(order.getCourier()));
        }

        orderDto.setId(order.getId());
        orderDto.setDate(order.getDate().getTime() / 1000);
        orderDto.setDescription(order.getDescription());
        orderDto.setPrice(order.getPrice());
        orderDto.setStatus(order.getStatus());
        orderDto.setCustomer(UserDto.toDto(order.getUser()));

        return orderDto;
    }

    public static List<OrderDto> toDto(List<Order> orders) {
        List<OrderDto> ordersDtos = new ArrayList<>();

        for (Order order : orders) {
            ordersDtos.add(OrderDto.toDto(order));
        }

        return ordersDtos;
    }

}
