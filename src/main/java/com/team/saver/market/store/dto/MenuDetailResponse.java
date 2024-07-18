package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuDetailResponse {

    private long menuId;

    private int price;

    private String imageUrl;

    private String menuName;

    private List<MenuOptionResponse> options;

}
