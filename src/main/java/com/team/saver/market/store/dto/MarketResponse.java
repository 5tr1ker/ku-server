package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    private String eventMessage;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String closedDays;

    private double averageReviewScore;

    private long reviewCount;

    private int maxDiscountRate;

    @Setter
    private double distance;

    public MarketResponse(long marketId, MainCategory mainCategory, double locationX, double locationY, String marketImage, String marketName, String marketDescription, String detailAddress, String eventMessage, LocalTime openTime, LocalTime closeTime, String closedDays, double averageReviewScore, long reviewCount, int maxDiscountRate) {
        this.marketId = marketId;
        this.mainCategory = mainCategory;
        this.locationX = locationX;
        this.locationY = locationY;
        this.marketImage = marketImage;
        this.marketName = marketName;
        this.marketDescription = marketDescription;
        this.detailAddress = detailAddress;
        this.eventMessage = eventMessage;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.closedDays = closedDays;
        this.averageReviewScore = averageReviewScore;
        this.reviewCount = reviewCount;
        this.maxDiscountRate = maxDiscountRate;
    }
}
