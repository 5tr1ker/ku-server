package com.team.saver.market.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasketMenuResponse {

    private long basketMenuId;

    private String menuName;

    private String menuImageUrl;

    private int menuPrice;

    private long amount;

    private String optionDescription;

    private int optionAdditionalPrice;

    private int totalMenuPrice;

}
