package com.team.saver.market.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {

    private long orderId;

    private String content;

    private int score;

}
