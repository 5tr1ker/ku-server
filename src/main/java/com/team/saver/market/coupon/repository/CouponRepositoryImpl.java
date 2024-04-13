package com.team.saver.market.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.coupon.dto.CouponResponse;

import lombok.RequiredArgsConstructor;

import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.store.entity.QMarket.market;

import java.util.List;

@RequiredArgsConstructor
public class CouponRepositoryImpl implements CustomCouponRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CouponResponse> findByMarketId(long marketId) {
        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        coupon.couponName,
                        coupon.couponDescription,
                        coupon.saleRate
                ))
                .from(coupon)
                .innerJoin(coupon.market).on(market.marketId.eq(marketId))
                .fetch();
    }
}
