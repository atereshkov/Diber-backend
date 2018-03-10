package com.github.handioq.diber.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.handioq.diber.model.base.AuditableEntity;
import com.github.handioq.diber.model.dto.AddressDto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address extends AuditableEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;

    @Column(name = "postal_code")
    private int postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "addressFrom", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Order> orders;

    public Address() {
    }

    public Address(String name, int postalCode, String country, String city, String region, String address, String phone) {
        this.name = name;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.region = region;
        this.address = address;
        this.phone = phone;
    }

    public Address(String name, int postalCode, String country, String city,
                   String region, String address, String phone, double longitude, double latitude) {
        this.name = name;
        this.postalCode = postalCode;
        this.country = country;
        this.city = city;
        this.region = region;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @JsonIgnore
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public static Address toEntity(AddressDto addressDto) {
        return new Address(addressDto.getName(), addressDto.getPostalCode(), addressDto.getCountry(),
                addressDto.getCity(), addressDto.getRegion(), addressDto.getAddress(),
                addressDto.getPhone(), addressDto.getLongitude(), addressDto.getLatitude());
    }

}
