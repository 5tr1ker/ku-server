package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewStatisticResponse {

    private long canWriteReviewCount;

    private long wroteReviewCount;

}
