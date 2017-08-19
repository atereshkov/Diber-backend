package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.handioq.diber.model.base.BaseDto;
import com.github.handioq.diber.model.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewDto extends BaseDto {

    @JsonProperty("review")
    private String review;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("courier")
    private UserDto courier;

    public ReviewDto() { }

    public ReviewDto(String review, int rating, UserDto user, UserDto courier) {
        this.review = review;
        this.rating = rating;
        this.user = user;
        this.courier = courier;
    }

    public UserDto getCourier() {
        return courier;
    }

    public void setCourier(UserDto courier) {
        this.courier = courier;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "review='" + review + '\'' +
                ", rating=" + rating +
                ", user=" + user +
                '}';
    }

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(review.getReview(), review.getRating(),
                UserDto.toDto(review.getUser()), UserDto.toDto(review.getCourier()));
    }

    public static List<ReviewDto> toDto(List<Review> reviews) {
        List<ReviewDto> reviewsDto = new ArrayList<>();

        for (Review review : reviews) {
            reviewsDto.add(ReviewDto.toDto(review));
        }

        return reviewsDto;
    }

}
