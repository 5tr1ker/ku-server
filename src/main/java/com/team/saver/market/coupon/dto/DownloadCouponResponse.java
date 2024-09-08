package com.team.saver.market.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DownloadCouponResponse {

    private long downloadCouponId;

    private boolean isUsage;

    private String couponName;

    private String couponDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime expireDate;

    private int conditionToUseAmount;

    private int saleRate;

    private long marketId;

    private String marketName;
}
