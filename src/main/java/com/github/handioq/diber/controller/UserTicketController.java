package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.TicketDto;
import com.github.handioq.diber.model.entity.Ticket;
import com.github.handioq.diber.model.entity.User;
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

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_TICKETS)
public class UserTicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTicketController.class);

    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public UserTicketController(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
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
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addTicket(@AuthenticationPrincipal User userPrincipal,
                                       @RequestBody TicketDto ticketDto,
                                       @PathVariable("user_id") long userId) {
        User user = userService.findOne(userPrincipal.getId());

        Ticket ticket = Ticket.toEntity(ticketDto);
        ticket.setStatus("New");
        ticket.setUser(user);
        user.getTickets().add(ticket);
        userService.saveOrUpdate(user);

        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }


}
