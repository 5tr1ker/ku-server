package com.team.saver.market.basket.dto;

import lombok.Getter;

@Getter
public class BasketCreateRequest {

    private long menuId;

    private long marketId;

    private long amount;

}
