package com.team.saver.market.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequest {

    private String title;

    private long orderId;

    private String content;

    private int score;

}
