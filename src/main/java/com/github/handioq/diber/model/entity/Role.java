package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.BaseEntity;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles") // cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    @JsonIgnore
    private Set<User> users = new HashSet<User>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Role toEntity(Role roleDto) {
        return new Role(roleDto.getName());
    }

}
