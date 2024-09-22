package com.team.saver.market.basket.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class BasketMenuResponse {

    private long basketMenuId;

    private String menuName;

    private String menuImageUrl;

    private int menuPrice;

    private long amount;

    private long optionTotalPrice;


    public BasketMenuResponse(long basketMenuId, String menuName, String menuImageUrl, int menuPrice, long amount, Object optionTotalPrice) {
        this.basketMenuId = basketMenuId;
        this.menuName = menuName;
        this.menuImageUrl = menuImageUrl;
        this.menuPrice = menuPrice;
        this.amount = amount;
        this.optionTotalPrice = Long.valueOf(optionTotalPrice.toString());
    }

    private long totalPrice = 0;

    @Setter
    private List<BasketOptionResponse> options;

    public void calculateTotalPrice() {
        this.totalPrice = ( this.optionTotalPrice + menuPrice ) * this.amount;
    }

}
