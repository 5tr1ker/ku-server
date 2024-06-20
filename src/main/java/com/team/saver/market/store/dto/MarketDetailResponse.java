package com.team.saver.market.store.dto;

import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
