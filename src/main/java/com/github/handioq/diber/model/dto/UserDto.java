package com.github.handioq.diber.model.dto;

import com.github.handioq.diber.model.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private long id;
    private String email;
    private String username;
    private String password;
    private boolean enabled;
    private boolean isCustomer;
    private boolean isCourier;
    private String fullname;

    public UserDto() {
    }

    public UserDto(long id, String email, String username, String password, boolean enabled) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public UserDto(long id, String email, String username, String password, boolean enabled, String fullname) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    public boolean isCourier() {
        return isCourier;
    }

    public void setCourier(boolean courier) {
        isCourier = courier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(),
                user.isEnabled(), user.getFullname());
    }

    public static List<UserDto> toDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(UserDto.fromEntity(user));
        }

        return userDtos;
    }

}
