package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponse {

    private long menuId;

    private int price;

    private String description;

    private String imageUrl;

    private String menuName;

}