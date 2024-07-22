package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuOptionClassificationResponse {

    private String classification;

    private boolean isMultipleSelection;

    private List<MenuOptionResponse> options;

}
