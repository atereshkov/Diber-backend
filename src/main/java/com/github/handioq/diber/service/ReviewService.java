package com.github.handioq.diber.service;

import com.github.handioq.diber.model.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> findByUserId(long userId);

    List<Review> findByCourierId(long courierId);

}