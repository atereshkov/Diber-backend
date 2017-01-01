package com.github.handioq.diber.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public abstract class BaseEntity implements Serializable { // FIXME: 05-Oct-16 problem with id not found

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
