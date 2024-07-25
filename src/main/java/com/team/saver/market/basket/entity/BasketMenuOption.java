package com.team.saver.market.basket.entity;

import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BasketMenuOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long basketMenuOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    private BasketMenu basketMenu;

    public static BasketMenuOption createEntity(MenuOption menuOption, BasketMenu basketMenu) {
        return BasketMenuOption.builder()
                .menuOption(menuOption)
                .basketMenu(basketMenu)
                .build();
    }

}
