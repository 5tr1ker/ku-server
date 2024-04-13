package com.team.saver.market.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.saver.market.coupon.dto.CouponResponse;

import com.team.saver.market.coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.store.entity.QMarket.market;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Coupon> findByPartnerEmailAndCouponId(String partnerEmail, long couponId) {
        Coupon result = jpaQueryFactory.select(coupon)
                .from(coupon)
                .innerJoin(coupon.market, market)
                .innerJoin(market.partner, account).on(account.email.eq(partnerEmail))
                .where(coupon.couponId.eq(couponId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
