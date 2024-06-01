package com.team.saver.market.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DownloadCouponResponse {

    private long downloadCouponId;

    private boolean isUsage;

    private String couponName;

    private String couponDescription;

    private double saleRate;

    private int saleAmount;

    private long marketId;

    private String marketName;
}
