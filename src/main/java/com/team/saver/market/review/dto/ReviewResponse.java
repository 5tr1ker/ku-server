package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewResponse {

    private long reviewId;

    private long reviewerId;

    private String reviewerEmail;

    private String reviewContent;

    private long marketId;

    private String marketName;

    private int score;

}
