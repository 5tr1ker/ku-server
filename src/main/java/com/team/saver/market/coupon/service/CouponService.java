package com.team.saver.market.coupon.service;


import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.coupon.entity.DownloadCoupon;
import com.team.saver.market.coupon.repository.CouponDownloadRepository;
import com.team.saver.market.coupon.repository.CouponRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final MarketRepository marketRepository;
    private final CouponRepository couponRepository;
    private final AccountRepository accountRepository;
    private final CouponDownloadRepository couponDownloadRepository;

    public List<CouponResponse> findCouponByMarketId(long marketId) {
        return couponRepository.findByMarketId(marketId);
    }

    @Transactional
    public void downloadCoupon(UserDetails userDetails, long couponId) {
        if(couponRepository.findDownloadCouponByCouponIdAndUserEmail(userDetails.getUsername(), couponId).isPresent()) {
            throw new CustomRuntimeException(EXIST_COUPON);
        }

        Account account = accountRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUNT_USER));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_COUPON));

        DownloadCoupon downloadCoupon = DownloadCoupon.createEntity(account, coupon, coupon.getMarket());
        couponDownloadRepository.save(downloadCoupon);
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

    @Transactional
    public void updateCouponUsage(UserDetails userDetails, long downloadCouponId) {
        DownloadCoupon downloadCoupon = couponRepository.findDownloadCouponByIdAndUserEmail(userDetails.getUsername(), downloadCouponId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_COUPON));

        downloadCoupon.updateIsUsage();
    }

    public List<DownloadCouponResponse> findDownloadCouponByUserEmail(UserDetails userDetails) {
        return couponRepository.findDownloadCouponByUserEmail(userDetails.getUsername());
    }
}
