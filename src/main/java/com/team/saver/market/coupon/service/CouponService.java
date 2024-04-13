package com.team.saver.market.coupon.service;


import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final MarketRepository marketRepository;
    private final CouponRepository couponRepository;

    public List<CouponResponse> findCouponByMarketId(long marketId) {
        return couponRepository.findByMarketId(marketId);
    }

    public void downloadCoupon(UserDetails userDetails, long couponId) {

    }

    public void createCoupon(CouponCreateRequest request, long marketId) {

    }

    public void deleteCoupon(UserDetails userDetails, long couponId) {

    }

    public void updateCouponUsage(UserDetails userDetails, long couponId) {

    }

}
