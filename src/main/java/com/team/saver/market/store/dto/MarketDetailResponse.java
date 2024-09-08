package com.team.saver.market.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private int cookingTime;

    private String detailAddress;

    private String eventMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime openTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime closeTime;

    private String closeDays;

    private String marketPhone;

    private double averageReviewScore;

    private long reviewCount;

}
