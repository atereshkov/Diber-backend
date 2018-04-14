package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.AuditableEntity;
import com.github.handioq.diber.model.dto.TicketDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket extends AuditableEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Message> messages;

    public Ticket() {
    }

    public Ticket(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public static Ticket toEntity(TicketDto ticketDto) {
        return new Ticket(ticketDto.getTitle(), ticketDto.getStatus());
    }

}
