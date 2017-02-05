package com.github.handioq.diber.model.dto;

public class OrderDto {

    private String date;
    private String description;
    private Double price;
    private String status;
    private String addressFrom;
    private String addressTo;
    private ShopDto shop;

    public OrderDto() {
    }

    public OrderDto(String date, String description, Double price, String status, String addressFrom, String addressTo, ShopDto shop) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.shop = shop;
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

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public ShopDto getShop() {
        return shop;
    }

    public void setShop(ShopDto shop) {
        this.shop = shop;
    }
}
