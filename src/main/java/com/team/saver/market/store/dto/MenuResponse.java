package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponse {

    private long menuId;
    private long price;
    private String menuName;

}