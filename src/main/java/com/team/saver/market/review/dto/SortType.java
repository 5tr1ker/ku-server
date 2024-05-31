package com.team.saver.market.review.dto;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.review.entity.QReviewRecommender.reviewRecommender;

@Getter
@RequiredArgsConstructor
public enum SortType {

    RECENTLY_UPLOAD("recentlyUpload" , review.writeTime.desc(), review.reviewId.desc()),
    MOST_RECOMMEND("mostRecommend" , reviewRecommender.count().desc(), review.score.desc()),
    HIGHEST_SCORE("highestScore" , review.score.desc(), reviewRecommender.count().desc()),
    LOWEST_SCORE("lowestScore" , review.score.asc(), reviewRecommender.count().desc());


    private final String type;

    private final OrderSpecifier firstOrderSpecifier;

    private final OrderSpecifier secondOrderSpecifier;

}
