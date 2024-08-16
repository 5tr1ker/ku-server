package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionResponse {

    private long menuOptionId;

    private String description;

    private int optionPrice;

    private boolean isAdultMenu;

    private boolean isDefaultOption;

}
