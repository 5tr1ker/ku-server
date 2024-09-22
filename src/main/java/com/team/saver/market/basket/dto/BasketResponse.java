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

    public BasketResponse(long basketId, long marketId, String marketName, List<BasketMenuResponse> menu) {
        this.basketId = basketId;
        this.marketId = marketId;
        this.marketName = marketName;
        this.menu = menu;
    }

    private long totalPrice = 0;

    public void calculateTotalPrice() {
        for(BasketMenuResponse response : menu) {
            totalPrice += response.getTotalPrice();
        }
    }

    private boolean isDiscountAvailability = false;

    private long discountAmount = 0;

    private int discountRate = 0;

    public void setDiscount(long value) {
        if(value != 0) {
            this.isDiscountAvailability = true;

            this.discountAmount = value;

            double discountRateTemp = (double) (totalPrice - value) / (double) totalPrice;
            this.discountRate = (int) (100 - ( discountRateTemp * 100 ));
        }
    }
}
