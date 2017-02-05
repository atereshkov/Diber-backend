package com.github.handioq.diber.model.dto;

public class ShopDto {

    private String name;
    private String address;

    public ShopDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ShopDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
