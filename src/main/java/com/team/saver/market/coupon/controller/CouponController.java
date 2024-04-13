package com.team.saver.market.coupon.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.dto.DownloadCouponResponse;
import com.team.saver.market.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    @Operation(summary = "marketId 로 다운로드 가능한 쿠폰 모두 조회")
    public ResponseEntity findCouponByMarketId(@RequestParam long marketId) {
        List<CouponResponse> result = couponService.findCouponByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/download")
    @Operation(summary = "다운로드한 쿠폰 조회")
    public ResponseEntity findDownloadCouponByUserEmail(@LogIn CurrentUser currentUser) {
        List<DownloadCouponResponse> result = couponService.findDownloadCouponByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{couponId}/download")
    @Operation(summary = "쿠폰 다운로드")
    public ResponseEntity downloadCoupon(@LogIn CurrentUser currentUser, @PathVariable long couponId) {

        couponService.downloadCoupon(currentUser, couponId);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "새로운 쿠폰 생성")
    public ResponseEntity createCoupon(@LogIn CurrentUser currentUser,
                                       @RequestBody CouponCreateRequest request) {

        couponService.createCoupon(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{couponId}")
    @Operation(summary = "생성한 쿠폰 삭제")
    public ResponseEntity deleteCoupon(@LogIn CurrentUser currentUser,
                                       @PathVariable long couponId) {

        couponService.deleteCoupon(currentUser, couponId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/download/{downloadCouponId}")
    @Operation(summary = "사용한 쿠폰으로 수정")
    public ResponseEntity updateCouponUsage(@LogIn CurrentUser currentUser,
                                            @PathVariable long downloadCouponId) {

        couponService.updateCouponUsage(currentUser, downloadCouponId);

        return ResponseEntity.ok().build();
    }

}
