package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.entity.DownloadCoupon;

import java.util.List;
import java.util.Optional;

public interface CustomCouponRepository {

    List<CouponResponse> findByMarketIdAndIsDownload(String email, long marketId);

    List<Coupon> findByMarketIdWithoutDownloadCoupon(String email, long marketId);

    Optional<Coupon> findByPartnerEmailAndCouponId(String partnerEmail, long couponId);

    Optional<DownloadCoupon> findDownloadCouponByCouponIdAndUserEmail(String email, long couponId);

    Optional<DownloadCoupon> findDownloadCouponByIdAndUserEmail(String email, long id);

    List<DownloadCouponResponse> findDownloadCouponByUserEmail(String userEmail);

    long countUsedCouponByEmail(String email);

    Optional<Coupon> findByIdAndMarketId(long couponId, long marketId);

    List<CouponResponse> findCouponThatCanBeUsedFromDownloadCoupon(String email, long marketId, long orderPrice);

    long findDownloadCouponCountByUserEmail(String email);
}
