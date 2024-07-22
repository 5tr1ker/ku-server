package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderMenuResponse {

    private long orderMenuId;

    private String menuName;

    private String optionDescription;

    private int price;

}
