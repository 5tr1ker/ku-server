package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class MarketDetailResponse {

    private long marketId;

    private MainCategory mainCategory;

    private String marketImage;

    private String marketName;

    private String marketDescription;

    private int minimumOrderPrice;

    private String detailAddress;

    private String eventMessage;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String closeDays;

    private String marketPhone;

    private double averageReviewScore;

    private long reviewCount;

}
