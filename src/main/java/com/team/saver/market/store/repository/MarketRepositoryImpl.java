package com.team.saver.market.store.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.store.dto.MarketDetailResponse;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.dto.MenuResponse;
import com.team.saver.market.store.entity.Market;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.team.saver.market.store.entity.QMenu.menu;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.store.entity.QMarket.market;

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
                        market.openTime,
                        market.closeTime,
                        market.closedDays,
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
    public List<MarketResponse> findMarketsByConditional(BooleanExpression conditional) {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        market.openTime,
                        market.closeTime,
                        market.closedDays,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .where(conditional)
                .fetch();
    }

    @Override
    public List<MarketResponse> findMarketsAndSort(OrderSpecifier orderSpecifier, BooleanExpression conditional) {
        JPAQuery<MarketResponse> query = jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        market.openTime,
                        market.closeTime,
                        market.closedDays,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .orderBy(orderSpecifier);

        if (conditional != null) {
            query.where(conditional);
        }

        return query.fetch();
    }

    @Override
    public Optional<MarketDetailResponse> findMarketDetailById(long marketId) {
        MarketDetailResponse result = jpaQueryFactory.select(Projections.constructor(
                        MarketDetailResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.marketImage,
                        market.marketName,
                        market.marketDescription,
                        market.minimumOrderPrice,
                        market.detailAddress,
                        market.openTime,
                        market.closeTime,
                        market.closedDays,
                        market.marketPhone,
                        review.score.avg(),
                        review.countDistinct()
                ))
                .from(market)
                .innerJoin(market.reviews, review)
                .groupBy(market)
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

    @Override
    public List<MenuResponse> findMarketMenuById(long marketId) {
        return jpaQueryFactory.select(
                        Projections.constructor(MenuResponse.class,
                                menu.menuId,
                                menu.price,
                                menu.menuName
                        )
                )
                .from(market)
                .leftJoin(market.menus, menu)
                .where(market.marketId.eq(marketId))
                .fetch();
    }
}
