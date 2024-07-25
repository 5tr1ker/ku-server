package com.team.saver.market.basket.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MenuOptionUpdateRequest {

    private List<Long> menuOptionIds;

    private long amount;

}
