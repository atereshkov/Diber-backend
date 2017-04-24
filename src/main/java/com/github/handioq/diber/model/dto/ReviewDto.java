package com.github.handioq.diber.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDto {

    @JsonProperty("review")
    private String review;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("user")
    private UserDto user;

    public ReviewDto() { }

    public ReviewDto(String review, int rating, UserDto user) {
        this.review = review;
        this.rating = rating;
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
}
