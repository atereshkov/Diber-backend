package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.dto.MessageDto;
import com.github.handioq.diber.model.dto.TicketDto;
import com.github.handioq.diber.model.entity.Message;
import com.github.handioq.diber.model.entity.Ticket;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.MessageService;
import com.github.handioq.diber.service.TicketService;
import com.github.handioq.diber.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_TICKETS)
public class UserTicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTicketController.class);

    private final TicketService ticketService;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public UserTicketController(TicketService ticketService, UserService userService, MessageService messageService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getTickets(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId, Pageable pageable) {
        Page<Ticket> tickets = ticketService.findByUserId(userId, pageable);
        Page<TicketDto> ticketsDtos = tickets.map(TicketDto::toDto);
        return new ResponseEntity<>(ticketsDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getTickets(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId) {
        List<Ticket> tickets = ticketService.findByUserId(userId);
        List<TicketDto> ticketsDtos = TicketDto.toDto(tickets);
        return new ResponseEntity<>(ticketsDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{ticket_id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getTicketById(@AuthenticationPrincipal User userPrincipal,
                                           @PathVariable("ticket_id") long ticketId,
                                           @PathVariable("user_id") long userId) {
        Ticket ticket = ticketService.getById(ticketId);

        if (ticket == null) {
            return new ResponseEntity<>("Ticket not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(TicketDto.toDto(ticket), HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTicket(@AuthenticationPrincipal User userPrincipal,
                                       @RequestBody TicketDto ticketDto,
                                       @PathVariable("user_id") long userId) {
        User user = userService.findOne(userPrincipal.getId());

        ticketDto.setStatus("New");
        Ticket ticket = Ticket.toEntity(ticketDto);
        ticket.setUser(user);
        user.getTickets().add(ticket);
        userService.saveOrUpdate(user);

        // TODO return DTO of the newly created ticket
        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{ticket_id}/messages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addMessage(@AuthenticationPrincipal User userPrincipal,
                                        @RequestBody MessageDto messageDto,
                                        @PathVariable("ticket_id") long ticketId,
                                        @PathVariable("user_id") long userId) {

        if (messageDto.getMsg().isEmpty()) {
            ErrorResponseDto error = new ErrorResponseDto("Empty", "Message is empty");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        User user = userService.findOne(userPrincipal.getId());
        Ticket ticket = ticketService.getById(ticketId);

        Message message = Message.toEntity(messageDto);
        message.setTicket(ticket);
        message.setUser(user);
        ticket.getMessages().add(message);
        ticket.setLastModifiedDate(new Date());
        user.getMessages().add(message);
        user.getTickets().add(ticket);
        userService.saveOrUpdate(user);

        return new ResponseEntity<>(TicketDto.toDto(ticket), HttpStatus.CREATED);
    }

}
