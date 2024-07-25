package com.team.saver.market.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderOptionResponse {

    private String optionDescription;

    private long optionPrice;

}
