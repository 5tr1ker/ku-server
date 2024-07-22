package com.team.saver.market.store.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MenuOptionContainerCreateRequest {

    private String classification;

    private boolean multipleSelection;

    private List<MenuOptionCreateRequest> menuOption;

}
