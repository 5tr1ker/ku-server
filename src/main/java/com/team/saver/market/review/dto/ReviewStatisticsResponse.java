package com.team.saver.market.review.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ReviewStatisticsResponse {

    List<ReviewStatistics> reviewStatistics;

    double averageScore;

    public static ReviewStatisticsResponse createDto(List<ReviewStatistics> reviewStatistics, double averageScore) {
        return new ReviewStatisticsResponse(reviewStatistics, averageScore);
    }

}
