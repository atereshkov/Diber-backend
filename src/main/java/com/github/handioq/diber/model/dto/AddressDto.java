package com.github.handioq.diber.model.dto;

import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressDto extends BaseDto {

    private long id;
    private String name;
    private int postalCode;
    private String country;
    private String city;
    private String region;
    private String address;
    private String phone;
    private long userId;
    private double longitude;
    private double latitude;

    public AddressDto() {
    }

    public AddressDto(long id, String name, int postalCode, String country, String city, String region,
                      String address, String phone, long userId, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressDto(long id, String name, int postalCode, String country, String city, String region, String address, String phone, long userId) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public static List<AddressDto> toDto(List<Address> entityItems) {
        List<AddressDto> addressDtos = new ArrayList<>();

        for (Address address : entityItems) {
            addressDtos.add(AddressDto.toDto(address));
        }

        return addressDtos;
    }

    public static AddressDto toDto(Address address) {
        return new AddressDto(address.getId(), address.getName(), address.getPostalCode(), address.getCountry(),
                address.getCity(), address.getRegion(), address.getAddress(), address.getPhone(), address.getUser().getId(),
                address.getLongitude(), address.getLatitude());
    }
}
