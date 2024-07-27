package com.team.saver.market.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.saver.market.coupon.dto.CouponResponse;

import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.entity.DownloadCoupon;
import lombok.RequiredArgsConstructor;

import static com.team.saver.market.coupon.entity.QDownloadCoupon.downloadCoupon;
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
                        coupon.conditionToUse,
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

    @Override
    public Optional<DownloadCoupon> findDownloadCouponByCouponIdAndUserEmail(String email, long couponId) {
        DownloadCoupon result = jpaQueryFactory.select(downloadCoupon)
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.coupon, coupon).on(coupon.couponId.eq(couponId))
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<DownloadCoupon> findDownloadCouponByIdAndUserEmail(String email, long id) {
        DownloadCoupon result = jpaQueryFactory.select(downloadCoupon)
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .where(downloadCoupon.downloadCouponId.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<DownloadCouponResponse> findDownloadCouponByUserEmail(String userEmail) {
        return jpaQueryFactory.select(Projections.constructor(
                        DownloadCouponResponse.class,
                        downloadCoupon.downloadCouponId,
                        downloadCoupon.isUsage,
                        coupon.couponName,
                        coupon.couponDescription,
                        coupon.saleRate,
                        market.marketId,
                        market.marketName
                ))
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.coupon, coupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(userEmail))
                .innerJoin(downloadCoupon.market, market)
                .fetch();
    }

    @Override
    public long countUsedCouponByEmail(String email) {
        return jpaQueryFactory.select(downloadCoupon.count())
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .where(downloadCoupon.isUsage.eq(true))
                .fetchOne();
    }

    @Override
    public Optional<Coupon> findByIdAndMarketId(long couponId, long marketId) {
        Coupon result = jpaQueryFactory.select(coupon)
                .from(coupon)
                .innerJoin(coupon.market, market).on(market.marketId.eq(marketId))
                .where(coupon.couponId.eq(couponId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<CouponResponse> findCouponThatCanBeUsedFromDownloadCoupon(String email, long marketId, long orderPrice) {
        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        coupon.couponName,
                        coupon.conditionToUse,
                        coupon.couponDescription,
                        coupon.saleRate
                ))
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .innerJoin(downloadCoupon.coupon, coupon).on(coupon.conditionToUseAmount.loe(orderPrice))
                .innerJoin(coupon.market).on(market.marketId.eq(marketId))
                .where(downloadCoupon.isUsage.eq(false))
                .fetch();
    }
}
