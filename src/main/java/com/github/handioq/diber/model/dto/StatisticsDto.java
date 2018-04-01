package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;

public class StatisticsDto extends BaseDto {

    @JsonProperty("users_count")
    private long usersCount;

    @JsonProperty("orders_count")
    private long ordersCount;

    @JsonProperty("addresses_count")
    private long addressesCount;

    public StatisticsDto() {
    }

    public StatisticsDto(long ordersCount, long addressesCount) {
        this.ordersCount = ordersCount;
        this.addressesCount = addressesCount;
    }

    public StatisticsDto(long usersCount, long ordersCount, long addressesCount) {
        this.usersCount = usersCount;
        this.ordersCount = ordersCount;
        this.addressesCount = addressesCount;
    }

    public long getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(long usersCount) {
        this.usersCount = usersCount;
    }

    public long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public long getAddressesCount() {
        return addressesCount;
    }

    public void setAddressesCount(long addressesCount) {
        this.addressesCount = addressesCount;
    }

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "usersCount=" + usersCount +
                ", ordersCount=" + ordersCount +
                '}';
    }
}
