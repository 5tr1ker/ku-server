package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PhotoReviewResponse {

    private List<ReviewImageResponse> reviewImages;

    private long reviewId;

}
