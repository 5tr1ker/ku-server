package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MenuOptionCreateRequest {

    private String description;

    private int additionalPrice;

}
