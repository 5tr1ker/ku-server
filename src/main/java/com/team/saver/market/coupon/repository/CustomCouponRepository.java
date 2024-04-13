package com.team.saver.market.coupon.repository;

import com.team.saver.market.coupon.dto.CouponResponse;

import java.util.List;

public interface CustomCouponRepository {

    List<CouponResponse> findByMarketId(long marketId);

}
