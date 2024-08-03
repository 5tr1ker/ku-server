package com.team.saver.market.coupon.dto;

import com.team.saver.market.coupon.entity.ConditionToUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponResponse {

    private long couponId;

    private String marketName;

    private String couponName;

    private ConditionToUse conditionToUse;

    private String couponDescription;

    private int conditionToUseAmount;

    private int saleRate;

}
