package com.team.saver.market.store.dto;

import com.team.saver.market.coupon.entity.Coupon;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MarketDetailResponse {

    private long marketId;

    private MainCategory mainCategory;

    private double locationX;

    private double locationY;

    private String marketName;

    private String marketDescription;

    private String detailAddress;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String marketPhone;

    @Builder.Default
    private List<MenuResponse> menus = new ArrayList<>();


    public static MarketDetailResponse createResponse(Market market) {
        List<MenuResponse> menusList = market.getMenus().stream()
                .map(MenuResponse::createResponse)
                .collect(Collectors.toList());

        return MarketDetailResponse.builder()
                .marketId(market.getMarketId())
                .mainCategory(market.getMainCategory())
                .locationX(market.getLocationX())
                .locationY(market.getLocationY())
                .marketName(market.getMarketName())
                .marketDescription(market.getMarketDescription())
                .detailAddress(market.getDetailAddress())
                .openTime(market.getOpenTime())
                .closeTime(market.getCloseTime())
                .marketPhone(market.getMarketPhone())
                .menus(menusList)
                .build();
    }

}
