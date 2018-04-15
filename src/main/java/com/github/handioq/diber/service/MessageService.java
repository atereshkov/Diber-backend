package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findAllByTicketId(long ticketId);

}
