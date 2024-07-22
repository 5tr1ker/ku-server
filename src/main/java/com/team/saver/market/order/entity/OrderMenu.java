package com.team.saver.market.order.entity;

import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.*;

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

    private String optionDescription;

    private long optionPrice;

    private long price;

    private long amount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    public static OrderMenu createEntity(BasketMenu basketMenu) {
        MenuOption menuOption = basketMenu.getMenuOption();
        Menu menu = basketMenu.getMenu();

        if(menuOption == null) {
            return OrderMenu.builder()
                    .menuName(menu.getMenuName())
                    .price(menu.getPrice())
                    .optionPrice(0)
                    .amount(basketMenu.getAmount())
                    .build();
        }

        return OrderMenu.builder()
                .menuName(menu.getMenuName())
                .optionDescription(menuOption.getDescription())
                .price(menu.getPrice() + menuOption.getOptionPrice())
                .optionPrice(menuOption.getOptionPrice())
                .build();
    }

}
