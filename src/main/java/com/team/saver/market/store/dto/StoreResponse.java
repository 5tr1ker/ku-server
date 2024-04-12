package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoreResponse {

    private long marketId;

    private MainCategory mainCategory;

    private double locationX;

    private double locationY;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    private double averageReviewScore;

    private long reviewCount;

    private double maxDiscountRate;

}
