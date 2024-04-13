package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CustomCouponRepository {

    List<CouponResponse> findByMarketId(long marketId);

    Optional<Coupon> findByPartnerEmailAndCouponId(String partnerEmail, long couponId);

}
