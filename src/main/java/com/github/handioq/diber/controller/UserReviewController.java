package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.ReviewDto;
import com.github.handioq.diber.model.entity.Review;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.ReviewService;
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
@RequestMapping(Constants.API_URL + Constants.URL_USER_REVIEW)
public class UserReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserReviewController.class);

    private final ReviewService reviewService;

    @Autowired
    public UserReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //@PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getReviews(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId) {
        LOGGER.info("Start getReviews userId: {}", userId);
        //List<Review> reviews = reviewService.findByUserId(userId); // todo separate this
        List<Review> reviews = reviewService.findByCourierId(userId);
        return new ResponseEntity<>(ReviewDto.toDto(reviews), HttpStatus.OK);
    }

}
