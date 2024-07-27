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

    private int conditionToUseAmount;

    private int saleRate;

    private long marketId;

    private String marketName;
}
