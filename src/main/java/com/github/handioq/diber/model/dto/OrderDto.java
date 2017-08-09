package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.handioq.diber.model.entity.Order;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private long id;
    private String date;
    private String description;
    private Double price;
    private String status;
    private ShopDto shop;
    private AddressDto address;
    private UserDto courier;
    private UserDto customer;
    // todo add array of requestDto

    public OrderDto() {
    }

    public OrderDto(long id, String date, String description, Double price, String status, ShopDto shop, AddressDto address) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.shop = shop;
        this.address = address;
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public ShopDto getShop() {
        return shop;
    }

    public void setShop(ShopDto shop) {
        this.shop = shop;
    }

    public static OrderDto fromEntity(Order order) {
        OrderDto orderDto = new OrderDto();

        if (order.getShop() != null) {
            orderDto.setShop(ShopDto.fromEntity(order.getShop()));
        }

        if (order.getAddress() != null) {
            orderDto.setAddress(AddressDto.fromEntity(order.getAddress()));
        }

        if (order.getCourier() != null) {
            orderDto.setCourier(UserDto.fromEntity(order.getCourier()));
        }

        orderDto.setId(order.getId());
        orderDto.setDate(order.getDate().toString());
        orderDto.setDescription(order.getDescription());
        orderDto.setPrice(order.getPrice());
        orderDto.setStatus(order.getStatus());
        orderDto.setCustomer(UserDto.fromEntity(order.getUser()));

        return orderDto;
    }

    public static List<OrderDto> toDto(List<Order> orders) {
        List<OrderDto> ordersDtos = new ArrayList<>();

        for (Order order : orders) {
            ordersDtos.add(OrderDto.fromEntity(order));
        }

        return ordersDtos;
    }

}
