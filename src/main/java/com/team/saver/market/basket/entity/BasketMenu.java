package com.team.saver.market.basket.entity;

import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE },orphanRemoval = true , mappedBy = "basketMenu")
    private List<BasketMenuOption> basketMenuOptions = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Basket basket;

    private long amount;

    public void updateMenuOption(List<MenuOption> menuOptions) {
        this.basketMenuOptions.clear();

        for(MenuOption menuOption : menuOptions) {
            BasketMenuOption basketMenuOption = BasketMenuOption.createEntity(menuOption, this);

            this.basketMenuOptions.add(basketMenuOption);
        }
    }

    public void updateAmount(long amount) {
        this.amount = amount;
    }

    public static BasketMenu createEntity(Menu menu, long amount) {
        return BasketMenu.builder()
                .menu(menu)
                .amount(amount)
                .build();
    }

}
