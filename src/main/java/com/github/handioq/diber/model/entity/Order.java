package com.github.handioq.diber.model.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "delivery_price")
    private Double price;

    @NotNull
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "address_from")
    private String addressFrom;

    @NotNull
    @Column(name = "address_to")
    private String addressTo;

    /*
    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Set<Image> images;
    */

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonManagedReference
    //@JsonBackReference // todo fix?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    //@JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Request> requests;

    public Order(Date date, String description, Double price, String status, Shop shop) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.shop = shop;
    }

    public Order() {
    }

    @JsonProperty("address_from")
    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    @JsonProperty("address_to")
    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    @JsonIgnore
    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    @JsonIgnore
    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonIgnore
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    //@JsonProperty("main_image")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Order order = (Order) o;

        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (description != null ? !description.equals(order.description) : order.description != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (addressFrom != null ? !addressFrom.equals(order.addressFrom) : order.addressFrom != null) return false;
        if (addressTo != null ? !addressTo.equals(order.addressTo) : order.addressTo != null) return false;
        if (shop != null ? !shop.equals(order.shop) : order.shop != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (courier != null ? !courier.equals(order.courier) : order.courier != null) return false;
        return requests != null ? requests.equals(order.requests) : order.requests == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (addressFrom != null ? addressFrom.hashCode() : 0);
        result = 31 * result + (addressTo != null ? addressTo.hashCode() : 0);
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (courier != null ? courier.hashCode() : 0);
        result = 31 * result + (requests != null ? requests.hashCode() : 0);
        return result;
    }
}