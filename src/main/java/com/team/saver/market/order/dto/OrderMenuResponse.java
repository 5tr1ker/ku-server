package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderMenuResponse {

    private long orderMenuId;

    private String menuName;

    private long price;

    private long amount;

    private List<OrderOptionResponse> options;

}
