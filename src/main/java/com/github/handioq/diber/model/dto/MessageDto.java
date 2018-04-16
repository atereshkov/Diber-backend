package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Message;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto extends BaseDto {

    @JsonProperty("id")
    private long id;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("created_date")
    private long created_date;

    @JsonProperty("updated_date")
    private long updated_date;

    @JsonProperty("user")
    private UserDto user;

    public MessageDto() {
    }

    public MessageDto(long id, String msg, long created_date, long updated_date, UserDto user) {
        this.id = id;
        this.msg = msg;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public static MessageDto toDto(Message message) {
        return new MessageDto(message.getId(), message.getMsg(),
                message.getCreatedDate().getTime() / 1000,
                message.getLastModifiedDate().getTime() / 1000,
                UserDto.toDto(message.getUser()));
    }

    public static List<MessageDto> toDto(List<Message> messages) {
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            messageDtos.add(MessageDto.toDto(message));
        }

        return messageDtos;
    }

}
