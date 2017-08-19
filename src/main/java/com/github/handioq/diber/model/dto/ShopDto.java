package com.github.handioq.diber.model.dto;

import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopDto extends BaseDto {

    private long id;
    private String name;
    private String address;

    public ShopDto() {
    }

    public ShopDto(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public static ShopDto toDto(Shop shop) {
        return new ShopDto(shop.getId(), shop.getName(), shop.getAddress());
    }

    public static List<ShopDto> toDto(List<Shop> shops) {
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shops) {
            shopDtos.add(ShopDto.toDto(shop));
        }

        return shopDtos;
    }

}
