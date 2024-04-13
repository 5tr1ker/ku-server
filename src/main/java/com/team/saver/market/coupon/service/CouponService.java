package com.team.saver.market.coupon.service;


import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.ONLY_ACCESS_OWNER_PARTNER;

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

    @Transactional
    public void createCoupon(UserDetails userDetails, CouponCreateRequest request, long marketId) {
        Market market = marketRepository.findMarketByMarketIdAndPartnerEmail(userDetails.getUsername(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_ACCESS_OWNER_PARTNER));

        Coupon coupon = Coupon.createCoupon(request, market);
        couponRepository.save(coupon);
    }

    @Transactional
    public void deleteCoupon(UserDetails userDetails, long couponId) {
        Coupon coupon = couponRepository.findByPartnerEmailAndCouponId(userDetails.getUsername(), couponId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_ACCESS_OWNER_PARTNER));

        couponRepository.delete(coupon);
    }

    public void updateCouponUsage(UserDetails userDetails, long couponId) {

    }

}
