package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewResponse {

    private long reviewId;

    private long reviewerId;

    private long reviewerEmail;

    private String reviewContent;

    private int score;

}
