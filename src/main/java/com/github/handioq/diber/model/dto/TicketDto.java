package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Ticket;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto extends BaseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("title")
    private String title;

    @JsonProperty("created_date")
    private long created_date;

    @JsonProperty("updated_date")
    private long updated_date;

    @JsonProperty("user")
    private UserDto user;

    public TicketDto() {
    }

    public TicketDto(long id, String status, String title, long created_date, long updated_date, UserDto user) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public long getCreated_date() {
        return created_date;
    }

    public void setCreated_date(long created_date) {
        this.created_date = created_date;
    }

    public long getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(long updated_date) {
        this.updated_date = updated_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static TicketDto toDto(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getStatus(), ticket.getTitle(),
                ticket.getCreatedDate().getTime() / 1000,
                ticket.getLastModifiedDate().getTime() / 1000,
                UserDto.toDto(ticket.getUser()));
    }

    public static List<TicketDto> toDto(List<Ticket> tickets) {
        List<TicketDto> ticketsDtos = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ticketsDtos.add(TicketDto.toDto(ticket));
        }

        return ticketsDtos;
    }

}