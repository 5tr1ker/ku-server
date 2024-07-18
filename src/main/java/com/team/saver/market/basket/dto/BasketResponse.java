package com.team.saver.market.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BasketResponse {

    private long basketId;

    private long marketId;

    private String marketName;

    private List<BasketMenuResponse> menu;

    private long totalPrice;

}
