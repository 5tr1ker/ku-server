package com.team.saver.market.coupon.dto;

import com.team.saver.market.coupon.entity.ConditionToUse;
import lombok.Getter;

@Getter
public class CouponCreateRequest {

    private String couponName;

    private int conditionToUseAmount;

    private ConditionToUse conditionToUse;

    private int saleRate;

}
