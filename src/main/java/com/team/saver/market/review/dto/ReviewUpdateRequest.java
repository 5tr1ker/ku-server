package com.team.saver.market.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ReviewUpdateRequest {

    private String content;

    private int score;

    private List<Long> removeImageId;

}
