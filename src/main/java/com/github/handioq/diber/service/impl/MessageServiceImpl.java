package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Message;
import com.github.handioq.diber.repository.MessageRepository;
import com.github.handioq.diber.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public List<Message> findAllByTicketId(long ticketId) {
        return messageRepository.findAllByTicketId(ticketId);
    }
}
