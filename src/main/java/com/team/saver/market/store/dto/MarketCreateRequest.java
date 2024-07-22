package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class MarketCreateRequest {

    private MainCategory mainCategory;

    private double locationX;

    private double locationY;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String closedDays;

    private String marketPhone;

}
