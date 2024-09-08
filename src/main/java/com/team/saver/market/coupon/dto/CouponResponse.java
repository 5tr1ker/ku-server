package com.team.saver.market.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.market.coupon.entity.ConditionToUse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CouponResponse {

    private long couponId;

    private String marketName;

    private String couponName;

    private ConditionToUse conditionToUse;

    private String couponDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expireDate;

    private int conditionToUseAmount;

    private int saleRate;

    private boolean isDownload;

}
