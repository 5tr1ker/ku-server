package com.team.saver.market.basket.entity;

import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasketMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long basketMenuId;

    @OneToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @OneToOne(fetch = FetchType.LAZY)
    private MenuOption menuOption;

    private long amount;

    public static BasketMenu createEntity(Menu menu, MenuOption menuOption, long amount) {
        return BasketMenu.builder()
                .menu(menu)
                .menuOption(menuOption)
                .amount(amount)
                .build();
    }

}
