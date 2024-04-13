package com.team.saver.market.coupon.controller;

import com.team.saver.market.coupon.dto.CouponCreateRequest;
import com.team.saver.market.coupon.dto.CouponResponse;
import com.team.saver.market.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{marketId}")
    @Operation(summary = "marketId 로 다운로드 가능한 쿠폰 모두 조회")
    public ResponseEntity findCouponByMarketId(@PathVariable long marketId) {
        List<CouponResponse> result = couponService.findCouponByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/download/{couponId}")
    @Operation(summary = "쿠폰 다운로드")
    public ResponseEntity downloadCoupon(@AuthenticationPrincipal UserDetails userDetails
            , @PathVariable long couponId) {

        couponService.downloadCoupon(userDetails, couponId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{marketId}")
    @Operation(summary = "새로운 쿠폰 생성")
    public ResponseEntity createCoupon(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestBody CouponCreateRequest request,
                             @PathVariable long marketId) {

        couponService.createCoupon(userDetails, request, marketId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{couponId}")
    @Operation(summary = "생성한 쿠폰 삭제")
    public ResponseEntity deleteCoupon(@AuthenticationPrincipal UserDetails userDetails,
                             @PathVariable long couponId) {

        couponService.deleteCoupon(userDetails, couponId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{couponId}")
    @Operation(summary = "사용한 쿠폰으로 수정")
    public ResponseEntity updateCouponUsage(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable long couponId) {

        couponService.updateCouponUsage(userDetails, couponId);

        return ResponseEntity.ok().build();
    }

}
