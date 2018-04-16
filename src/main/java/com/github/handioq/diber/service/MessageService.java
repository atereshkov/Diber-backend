package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Message;

import java.util.List;

public interface MessageService {

    List<Message> findByTicketId(long ticketId);

    Message getById(long messageId);

    void saveOrUpdate(Message message);

}
