package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.AuditableEntity;
import com.github.handioq.diber.model.dto.OrderDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends AuditableEntity<Long> {

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

    @Column(name = "current_longitude")
    private double longitude;

    @Column(name = "current_latitude")
    private double latitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_from_id")
    private Address addressFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_to_id")
    private Address addressTo;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Request> requests;

    public Order(Date date, String description, Double price, String status, double longitude, double latitude, User user, User courier, Address addressFrom, Address addressTo) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
        this.user = user;
        this.courier = courier;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public Order(Date date, String description, Double price, String status, Address addressFrom, Address addressTo) {
        this.date = date;
        this.description = description;
        this.price = price;
        this.status = status;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
    }

    public Order() {
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(Address addressFrom) {
        this.addressFrom = addressFrom;
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

    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(Address addressTo) {
        this.addressTo = addressTo;
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
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (courier != null ? !courier.equals(order.courier) : order.courier != null) return false;
        if (addressFrom != null ? !addressFrom.equals(order.addressFrom) : order.addressFrom != null) return false;
        return requests != null ? requests.equals(order.requests) : order.requests == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (courier != null ? courier.hashCode() : 0);
        result = 31 * result + (addressFrom != null ? addressFrom.hashCode() : 0);
        result = 31 * result + (requests != null ? requests.hashCode() : 0);
        return result;
    }

    public static Order toEntity(OrderDto orderDto) {
        //Shop shop = toShopEntity(orderDto.getShop());
        //Date date = DateUtil.getFromString(orderDto.getDate(), "yyyy-MM-dd HH:mm:ss");
        Date date = new Date(orderDto.getDate());
        return new Order(date, orderDto.getDescription(), orderDto.getPrice(), orderDto.getStatus(), null, null);
    }

}
