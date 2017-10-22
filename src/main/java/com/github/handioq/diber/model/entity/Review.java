package com.github.handioq.diber.model.entity;

import com.github.handioq.diber.model.base.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reviews")
public class Review extends AuditableEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "review")
    private String review;

    @Column(name = "rating")
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private User courier;

    public Review() {
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review='" + review + '\'' +
                ", rating=" + rating +
                ", user=" + user +
                '}';
    }
}
