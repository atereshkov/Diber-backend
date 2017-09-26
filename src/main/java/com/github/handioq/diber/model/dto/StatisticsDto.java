package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;

public class StatisticsDto extends BaseDto {

    @JsonProperty("users_count")
    private long usersCount;

    @JsonProperty("orders_count")
    private long ordersCount;

    public StatisticsDto() {
    }

    public StatisticsDto(long usersCount, long ordersCount) {
        this.usersCount = usersCount;
        this.ordersCount = ordersCount;
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

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "usersCount=" + usersCount +
                ", ordersCount=" + ordersCount +
                '}';
    }
}
