package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewStatisticsResponse {

    private List<ReviewStatisticsData> reviewStatisticData;

    private double averageScore;

    private long totalReviewCount;

    public static ReviewStatisticsResponse createDto(List<ReviewStatisticsData> reviewStatisticData, double averageScore, long totalReviewCount) {
        return new ReviewStatisticsResponse(reviewStatisticData, averageScore, totalReviewCount);
    }

}
