package com.team.saver.market.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponResponse {

    private long couponId;

    private String couponName;

    private String couponDescription;

    private double saleRate;

}
