package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class MenuResponse {
    private long price;
    private String name;

    public static MenuResponse createResponse(Menu menu) {
        return new MenuResponse(menu.getPrice(), menu.getMenuName());
    }
}