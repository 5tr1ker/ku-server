package com.team.saver.market.store.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.saver.market.store.dto.StoreResponse;
import com.team.saver.market.store.entity.MainCategory;
import lombok.RequiredArgsConstructor;

import static com.querydsl.core.types.dsl.Wildcard.countDistinct;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.store.entity.QMarket.market;

import java.util.List;

@RequiredArgsConstructor
public class MarketRepositoryImpl implements CustomMarketRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoreResponse> findMarkets() {
        return jpaQueryFactory.select(Projections.constructor(StoreResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .fetch();
    }

    @Override
    public List<StoreResponse> findMarketsByMainCategory(MainCategory category) {
        return jpaQueryFactory.select(Projections.constructor(StoreResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .where(market.mainCategory.eq(category))
                .fetch();
    }

    @Override
    public List<StoreResponse> findMarketsByMarketNameContaining(String marketName) {
        return jpaQueryFactory.select(Projections.constructor(StoreResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .where(market.marketName.contains(marketName))
                .fetch();
    }
}
