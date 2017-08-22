package com.github.handioq.diber.model.dto;

import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDto extends BaseDto {

    private String name;

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "name='" + name + '\'' +
                '}';
    }

    public static RoleDto toDto(Role role) {
        return new RoleDto(role.getName());
    }

    public static List<RoleDto> toDto(List<Role> roles) {
        List<RoleDto> roleDtoList = new ArrayList<>();

        for (Role role : roles) {
            roleDtoList.add(RoleDto.toDto(role));
        }

        return roleDtoList;
    }

}
