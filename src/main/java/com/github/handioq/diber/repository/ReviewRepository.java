package com.github.handioq.diber.repository;

import com.github.handioq.diber.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByUserId(long id);

    List<Review> findByCourierId(long id);

}