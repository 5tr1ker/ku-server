package com.team.saver.market.coupon.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.dto.LogInNotEssential;
import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/v1/markets/{marketId}/coupons")
    @Operation(summary = "[ 비-로그인 ] 해당 마켓에 다운로드 가능한 쿠폰 모두 조회 ( 115 )")
    public ResponseEntity findCouponByMarketId(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser, @PathVariable long marketId) {
        List<CouponResponse> result = couponService.findCouponByMarketId(currentUser, marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/coupons/downloads")
    @Operation(summary = "[ 로그인 ] 다운로드한 쿠폰 조회 ( 116 )")
    public ResponseEntity findDownloadCouponByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<DownloadCouponResponse> result = couponService.findDownloadCouponByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/coupons/downloads/counts")
    @Operation(summary = "[ 로그인 ] 다운로드한 쿠폰 갯수 조회 ( 117 )")
    public ResponseEntity findDownloadCouponCountByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        long result = couponService.findDownloadCouponCountByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketId}/coupons/downloads/can-use")
    @Operation(summary = "[ 로그인 ] 다운로드한 쿠폰에서 해당 주문에 사용할 수 있는 쿠폰 조회 ( 118 )")
    public ResponseEntity findCouponThatCanBeUsedFromDownloadCoupon(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                                    @PathVariable long marketId,
                                                                    @RequestParam long orderPrice) {
        List<CouponResponse> result = couponService.findCouponThatCanBeUsedFromDownloadCoupon(currentUser, marketId, orderPrice);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/markets/coupons/{couponId}/downloads")
    @Operation(summary = "[ 로그인 ] 쿠폰 다운로드 ( 119 )")
    public ResponseEntity downloadCoupon(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                         @PathVariable long couponId) {

        couponService.downloadCoupon(currentUser, couponId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/markets/{marketId}/coupons/downloads/all")
    @Operation(summary = "[ 로그인 ] 모든 쿠폰 다운로드 ( 120 )")
    public ResponseEntity downloadAllCoupon(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                            @PathVariable long marketId) {
        couponService.downloadAllCoupon(currentUser, marketId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/markets/{marketId}/coupons")
    @Operation(summary = "[ 로그인 ] 새로운 쿠폰 생성 ( 34 )")
    public ResponseEntity createCoupon(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                       @PathVariable long marketId,
                                       @RequestBody CouponCreateRequest request) {

        couponService.createCoupon(currentUser,marketId , request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/markets/coupons/{couponId}")
    @Operation(summary = "[ 로그인 ] 생성한 쿠폰 삭제 ( 35 )")
    public ResponseEntity deleteCoupon(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                       @PathVariable long couponId) {

        couponService.deleteCoupon(currentUser, couponId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/v1/markets/coupons/{downloadCouponId}/use")
    @Operation(summary = "[ 로그인 ] 다운로드한 쿠폰을 사용한 쿠폰으로 수정 ( 36 )")
    public ResponseEntity updateCouponUsage(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                            @PathVariable long downloadCouponId) {

        couponService.updateCouponUsage(currentUser, downloadCouponId);

        return ResponseEntity.ok().build();
    }

}
