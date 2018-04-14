package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    Ticket getById(long id);

    List<Ticket> findByUserId(long userId);

    void saveOrUpdate(Ticket ticket);

    Page<Ticket> findByUserId(long userId, Pageable pageable);

}