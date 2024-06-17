package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class MarketResponse {

    private long marketId;

    private MainCategory mainCategory;

    private double locationX;

    private double locationY;

    private String marketImage;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String closedDays;

    private double averageReviewScore;

    private long reviewCount;

    private int maxDiscountRate;

}
