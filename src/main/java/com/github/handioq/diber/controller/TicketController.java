package com.github.handioq.diber.controller;

import com.github.handioq.diber.service.TicketService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_TICKETS)
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // TODO

}
