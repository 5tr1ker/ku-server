package com.team.saver.market.basket.entity;

import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Basket basket;

    private long amount;

    public void updateMenuOption(MenuOption menuOption) {
        this.menuOption = menuOption;
    }

    public void updateAmount(long amount) {
        this.amount = amount;
    }

    public static BasketMenu createEntity(Menu menu, MenuOption menuOption, long amount) {
        return BasketMenu.builder()
                .menu(menu)
                .menuOption(menuOption)
                .amount(amount)
                .build();
    }

}
