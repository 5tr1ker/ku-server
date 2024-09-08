package com.team.saver.market.coupon.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.team.saver.market.coupon.dto.CouponResponse;

import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.entity.DownloadCoupon;
import com.team.saver.market.coupon.entity.QCoupon;
import lombok.RequiredArgsConstructor;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.market.coupon.entity.QDownloadCoupon.downloadCoupon;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.coupon.entity.QCoupon.coupon;
import static com.team.saver.market.store.entity.QMarket.market;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CouponRepositoryImpl implements CustomCouponRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CouponResponse> findByMarketIdAndIsDownload(String email, long marketId) {
        QCoupon qCoupon = new QCoupon("qCoupon2");

        StringTemplate castExpression = Expressions.stringTemplate("CONVERT({0}, CHAR(255))", email);

        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        market.marketName,
                        coupon.couponName,
                        coupon.conditionToUse,
                        coupon.couponDescription,
                        coupon.expireDate,
                        coupon.conditionToUseAmount,
                        coupon.saleRate,
                        select(downloadCoupon.isNotNull())
                                .from(downloadCoupon)
                                .innerJoin(downloadCoupon.coupon, qCoupon).on(qCoupon.eq(coupon))
                                .innerJoin(downloadCoupon.account, account).on(castExpression.eq(account.email))
                ))
                .from(coupon)
                .innerJoin(coupon.market).on(market.marketId.eq(marketId))
                .where(coupon.isDelete.eq(false).and(coupon.expireDate.gt(LocalDateTime.now())))
                .fetch();
    }

    @Override
    public List<Coupon> findByMarketIdWithoutDownloadCoupon(String email, long marketId) {
        return jpaQueryFactory.select(coupon)
                .from(coupon)
                .innerJoin(coupon.market, market).on(market.marketId.eq(marketId))
                .leftJoin(coupon.downloadCoupons, downloadCoupon).on(downloadCoupon.account.email.eq(email))
                .where(coupon.isDelete.eq(false).and(downloadCoupon.isNull()))
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
    public Optional<Coupon> findById(long couponId) {
        Coupon result = jpaQueryFactory.select(coupon)
                .from(coupon)
                .where(coupon.couponId.eq(couponId).and(coupon.isDelete.eq(false)))
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
                        coupon.expireDate,
                        coupon.conditionToUseAmount,
                        coupon.saleRate,
                        market.marketId,
                        market.marketName
                ))
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.coupon, coupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(userEmail))
                .innerJoin(downloadCoupon.market, market)
                .orderBy(coupon.priority.desc())
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
                .where(coupon.couponId.eq(couponId).and(coupon.expireDate.gt(LocalDateTime.now())))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<CouponResponse> findCouponThatCanBeUsedFromDownloadCoupon(String email, long marketId, long orderPrice) {
        return jpaQueryFactory.select(Projections.constructor(
                        CouponResponse.class,
                        coupon.couponId,
                        market.marketName,
                        coupon.couponName,
                        coupon.conditionToUse,
                        coupon.couponDescription,
                        coupon.expireDate,
                        coupon.conditionToUseAmount,
                        coupon.saleRate,
                        Expressions.booleanPath("true")
                ))
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .innerJoin(downloadCoupon.coupon, coupon).on(coupon.conditionToUseAmount.loe(orderPrice).and(coupon.expireDate.gt(LocalDateTime.now())))
                .innerJoin(coupon.market, market).on(market.marketId.eq(marketId).or(market.eventCouponMarket.eq(true)))
                .orderBy(coupon.priority.desc())
                .where(downloadCoupon.isUsage.eq(false))
                .fetch();
    }

    @Override
    public long findDownloadCouponCountByUserEmail(String email) {
        return jpaQueryFactory.select(downloadCoupon.count())
                .from(downloadCoupon)
                .innerJoin(downloadCoupon.account, account).on(account.email.eq(email))
                .innerJoin(downloadCoupon.coupon, coupon).on(coupon.expireDate.gt(LocalDateTime.now()))
                .fetchOne();
    }
}
