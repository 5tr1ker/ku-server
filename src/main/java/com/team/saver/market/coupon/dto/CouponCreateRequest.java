package com.team.saver.market.coupon.dto;

import com.team.saver.market.coupon.entity.ConditionToUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponCreateRequest {

    private String couponName;

    private int conditionToUseAmount;

    private ConditionToUse conditionToUse;

    private int saleRate;

}
