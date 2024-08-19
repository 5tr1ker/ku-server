package com.team.saver.market.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortType {

    RECENTLY_UPLOAD("recentlyUpload"),
    MOST_RECOMMEND("mostRecommend"),
    HIGHEST_SCORE("highestScore"),
    LOWEST_SCORE("lowestScore");

    private final String type;

}
