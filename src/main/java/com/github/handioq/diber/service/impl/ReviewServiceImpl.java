package com.github.handioq.diber.service.impl;

import com.github.handioq.diber.model.entity.Review;
import com.github.handioq.diber.repository.ReviewRepository;
import com.github.handioq.diber.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<Review> findByUserId(long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> findByCourierId(long courierId) {
        return reviewRepository.findByCourierId(courierId);
    }
}