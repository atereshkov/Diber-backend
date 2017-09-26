package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.RequestDto;
import com.github.handioq.diber.model.entity.Request;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.RequestService;
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

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_REQUEST)
public class UserRequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRequestController.class);

    private final RequestService requestService;

    @Autowired
    public UserRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#user, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRequests(@AuthenticationPrincipal User user,
                                         @PathVariable("user_id") long userId) {
        LOGGER.info("Start getRequests userId: {}", userId);
        List<Request> requests = requestService.findByCourierId(userId);
        List<RequestDto> requestsDtos = RequestDto.toDto(requests);
        return new ResponseEntity<>(requestsDtos, HttpStatus.OK);
    }

}
