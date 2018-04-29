package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.MessageDto;
import com.github.handioq.diber.model.dto.TicketDto;
import com.github.handioq.diber.model.entity.Message;
import com.github.handioq.diber.model.entity.Ticket;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.MessageService;
import com.github.handioq.diber.service.TicketService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_TICKETS)
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final TicketService ticketService;
    private final MessageService messageService;

    @Autowired
    public TicketController(TicketService ticketService, MessageService messageService) {
        this.ticketService = ticketService;
        this.messageService = messageService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userPrincipal.id)")
    @RequestMapping(value = "/{ticket_id}/messages", method = RequestMethod.GET)
    public ResponseEntity<?> getTicketMessages(@AuthenticationPrincipal User userPrincipal,
                                               @PathVariable("ticket_id") long ticketId) {
        List<Message> messages = messageService.findByTicketId(ticketId);
        List<MessageDto> messagesDtos = MessageDto.toDto(messages);
        return new ResponseEntity<>(messagesDtos, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getTickets(@AuthenticationPrincipal User userPrincipal,
                                        Pageable pageable) {
        Page<Ticket> tickets = ticketService.findAllByPage(pageable);
        Page<TicketDto> ticketsDtos = tickets.map(TicketDto::toDto);
        return new ResponseEntity<>(ticketsDtos, HttpStatus.OK);
    }

}
