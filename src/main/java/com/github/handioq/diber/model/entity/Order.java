package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.BaseEntity;
import com.github.handioq.diber.model.dto.OrderDto;
import com.github.handioq.diber.utils.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Request> requests;

    public Order(Date date, String description, Double price, String status, Shop shop, Address address) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.shop = shop;
        this.address = address;
    }

    public Order() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

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
        if (shop != null ? !shop.equals(order.shop) : order.shop != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (courier != null ? !courier.equals(order.courier) : order.courier != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        return requests != null ? requests.equals(order.requests) : order.requests == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (shop != null ? shop.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (courier != null ? courier.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (requests != null ? requests.hashCode() : 0);
        return result;
    }

    public static Order toEntity(OrderDto orderDto) {
        //Shop shop = toShopEntity(orderDto.getShop());
        Date date = DateUtil.getFromString(orderDto.getDate(), "yyyy-MM-dd HH:mm:ss");
        return new Order(date, orderDto.getDescription(), orderDto.getPrice(), orderDto.getStatus(), null, null);
    }

}
