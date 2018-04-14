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

    public TicketDto() {
    }

    public TicketDto(long id, String status, String title) {
        this.id = id;
        this.status = status;
        this.title = title;
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
        return new TicketDto(ticket.getId(), ticket.getStatus(), ticket.getTitle());
    }

    public static List<TicketDto> toDto(List<Ticket> tickets) {
        List<TicketDto> ticketsDtos = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ticketsDtos.add(TicketDto.toDto(ticket));
        }

        return ticketsDtos;
    }

}