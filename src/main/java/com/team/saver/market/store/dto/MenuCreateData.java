package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuCreateData {

    private String menuName;

    private String description;

    private int price;

    private List<MenuOptionContainerCreateRequest> optionContainers;

}