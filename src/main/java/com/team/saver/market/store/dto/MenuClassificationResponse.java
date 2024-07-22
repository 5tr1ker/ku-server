package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuClassificationResponse {

    private String classification;

    private List<MenuResponse> menus;

}
