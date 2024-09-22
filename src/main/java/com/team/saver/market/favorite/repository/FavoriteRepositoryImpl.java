package com.team.saver.market.favorite.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.market.favorite.entity.Favorite;

import com.team.saver.market.store.dto.MarketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.favorite.entity.QFavorite.favorite;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.store.entity.QMarket.market;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements CustomFavoriteRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Favorite> findByUserEmailAndMarketId(String userEmail, long marketId) {
        Favorite result = jpaQueryFactory.select(favorite)
                .from(favorite)
                .innerJoin(favorite.market, market).on(market.marketId.eq(marketId))
                .innerJoin(favorite.account, account).on(account.email.eq(userEmail))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<MarketResponse> findFavoriteMarketByUserEmail(String email, NoOffset noOffset) {
        return jpaQueryFactory.select(Projections.constructor(MarketResponse.class,
                        market.marketId,
                        market.mainCategory,
                        market.locationX,
                        market.locationY,
                        market.marketImage,
                        market.marketName,
                        market.marketDescription,
                        market.detailAddress,
                        market.eventMessage,
                        market.openTime,
                        market.closeTime,
                        market.closedDays,
                        review.score.avg(),
                        review.countDistinct(),
                        coupon.saleRate.max()
                ))
                .from(favorite)
                .innerJoin(favorite.account, account).on(account.email.eq(email))
                .innerJoin(favorite.market, market)
                .leftJoin(market.coupons, coupon)
                .leftJoin(market.reviews, review)
                .groupBy(market)
                .where(market.marketId.gt(noOffset.getLastIndex()))
                .limit(noOffset.getSize())
                .fetch();
    }

    @Override
    public List<Favorite> findByUserEmailAndMarketIds(String email, List<Long> ids) {
        return jpaQueryFactory.select(favorite)
                .from(favorite)
                .innerJoin(favorite.account, account).on(account.email.eq(email))
                .innerJoin(favorite.market, market).on(market.marketId.in(ids))
                .fetch();
    }

    @Override
    public long findFavoriteMarketCountByUserEmail(String email) {
        return jpaQueryFactory.select(favorite.count())
                .from(favorite)
                .innerJoin(favorite.account, account).on(account.email.eq(email))
                .fetchOne();
    }
}
