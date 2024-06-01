package com.team.saver.market.store.util;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.team.saver.market.store.entity.QMarket.market;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.review.entity.QReview.review;

@Getter
@RequiredArgsConstructor
public enum SortType {

    NEAR_DISTANCE("nearDistance", null),
    HIGHEST_DISCOUNT("highestDiscount", coupon.saleRate.max().desc()),
    MANY_REVIEW_COUNT("manyReviewCount", review.count().desc()),
    HIGHEST_REVIEW_RATED("highestReviewRated", review.score.avg().desc()),
    RECENTLY_UPLOAD("recentlyUpload", market.marketId.desc());

    private final String type;

    private final OrderSpecifier orderSpecifier;

}
