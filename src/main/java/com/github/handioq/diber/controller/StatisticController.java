package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ClientStatisticsDto;
import com.github.handioq.diber.model.dto.StatisticsDto;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
import com.github.handioq.diber.service.OrderService;
import com.github.handioq.diber.service.TicketService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_STATISTICS)
public class StatisticController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticController.class);

    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final TicketService ticketService;

    @Autowired
    public StatisticController(UserService userService, OrderService orderService,
                               AddressService addressService, TicketService ticketService) {
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.ticketService = ticketService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getStatistic() {
        LOGGER.info("Start getStatistics");
        Long usersCount = userService.count();
        Long ordersCount = orderService.count();
        Long addressesCount = addressService.count();
        Long ticketsCount = ticketService.count();
        StatisticsDto statisticsDto = new StatisticsDto(usersCount, ordersCount, addressesCount, ticketsCount);
        return new ResponseEntity<>(statisticsDto, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStatistic(@AuthenticationPrincipal User userPrincipal,
                                          @PathVariable("user_id") long userId) {
        Long ordersCount = orderService.countByUserId(userId);
        Long addressesCount = addressService.countByUserId(userId);
        Long ticketsCount = ticketService.countByUserId(userId);
        String status = "In progress";
        Long activeOrdersCount = orderService.countByUserIdAndStatus(userId, status);
        ClientStatisticsDto statisticsDto = new ClientStatisticsDto(activeOrdersCount, ordersCount, addressesCount, ticketsCount);
        return new ResponseEntity<>(statisticsDto, HttpStatus.OK);
    }

}
