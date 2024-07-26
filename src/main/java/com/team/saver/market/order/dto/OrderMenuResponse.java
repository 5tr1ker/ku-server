package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderMenuResponse {

    private long orderMenuId;

    private String menuName;

    private long price;

    private long amount;

    public OrderMenuResponse(long orderMenuId, String menuName, long price, long amount) {
        this.orderMenuId = orderMenuId;
        this.menuName = menuName;
        this.price = price;
        this.amount = amount;
    }

    @Setter
    private List<OrderOptionResponse> options;

}
