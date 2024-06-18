package com.team.saver.market.coupon.dto;

import lombok.Getter;

@Getter
public class CouponCreateRequest {

    private String couponName;

    private String couponDescription;

    private int saleRate;

}
