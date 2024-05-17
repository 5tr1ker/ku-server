package com.team.saver.market.store.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.store.entity.QMarket.market;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MarketRepositoryImpl implements CustomMarketRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MarketResponse> findMarkets() {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
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
    public List<MarketResponse> findMarketsByMainCategory(MainCategory category) {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
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
    public List<MarketResponse> findMarketsByMainCategoryAndMarketName(MainCategory category, String marketName) {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
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
                .where(market.mainCategory.eq(category).and(market.marketName.contains(marketName)))
                .fetch();
    }

    @Override
    public List<MarketResponse> findMarketsByMarketName(String marketName) {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
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

    @Override
    public Optional<Market> findMarketDetailById(long marketId) {
        Market result = jpaQueryFactory.select(market)
                .from(market)
                .innerJoin(market.menus).fetchJoin()
                .where(market.marketId.eq(marketId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Market> findMarketByMarketIdAndPartnerEmail(String partnerEmail, long marketId) {
        Market result = jpaQueryFactory.select(market)
                .from(market)
                .innerJoin(market.partner, account).on(account.email.eq(partnerEmail))
                .where(market.marketId.eq(marketId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
