package com.github.handioq.diber.model.dto;

public class OrderDto {

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

    public OrderDto(String date, String description, Double price, String status, ShopDto shop, AddressDto address) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.shop = shop;
        this.address = address;
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
}
