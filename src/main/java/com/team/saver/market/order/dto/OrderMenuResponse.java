package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuResponse {

    private long orderMenuId;

    private String menuName;

    private long price;

    private String optionDescription;

    private long optionPrice;

    private long amount;

    private long finalPrice;

}
