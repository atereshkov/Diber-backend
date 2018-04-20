package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;

public class ClientStatisticsDto extends BaseDto {

    @JsonProperty("active_orders_count")
    private long activeOrdersCount;

    @JsonProperty("orders_count")
    private long ordersCount;

    @JsonProperty("addresses_count")
    private long addressesCount;

    @JsonProperty("tickets_count")
    private long ticketsCounts;

    public ClientStatisticsDto() {
    }

    public ClientStatisticsDto(long activeOrdersCount, long ordersCount, long addressesCount, long ticketsCounts) {
        this.activeOrdersCount = activeOrdersCount;
        this.ordersCount = ordersCount;
        this.addressesCount = addressesCount;
        this.ticketsCounts = ticketsCounts;
    }

    public long getActiveOrdersCount() {
        return activeOrdersCount;
    }

    public void setActiveOrdersCount(long activeOrdersCount) {
        this.activeOrdersCount = activeOrdersCount;
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

    public long getTicketsCounts() {
        return ticketsCounts;
    }

    public void setTicketsCounts(long ticketsCounts) {
        this.ticketsCounts = ticketsCounts;
    }
}
