package com.team.saver.market.order.entity;

import com.team.saver.market.store.entity.Menu;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderMenuId;

    private String menuName;

    private int price;

    public static OrderMenu createEntity(Menu menu) {
        return OrderMenu.builder()
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .build();
    }

}
