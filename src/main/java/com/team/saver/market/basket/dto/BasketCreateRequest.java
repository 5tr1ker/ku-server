package com.team.saver.market.basket.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BasketCreateRequest {

    private long menuId;

    private List<Long> menuOptionIds;

    private long amount;

}
