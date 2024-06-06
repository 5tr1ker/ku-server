package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.entity.DownloadCoupon;

import java.util.List;
import java.util.Optional;

public interface CustomCouponRepository {

    List<CouponResponse> findByMarketId(long marketId);

    Optional<Coupon> findByPartnerEmailAndCouponId(String partnerEmail, long couponId);

    Optional<DownloadCoupon> findDownloadCouponByCouponIdAndUserEmail(String email, long couponId);

    Optional<DownloadCoupon> findDownloadCouponByIdAndUserEmail(String email, long id);

    List<DownloadCouponResponse> findDownloadCouponByUserEmail(String userEmail);

    long countUsedCouponByEmail(String email);

}
