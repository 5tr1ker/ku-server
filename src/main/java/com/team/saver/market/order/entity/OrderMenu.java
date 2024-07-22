package com.team.saver.market.order.entity;

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

    private int price;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    public static OrderMenu createEntity(Menu menu, MenuOption menuOption) {
        if(menuOption == null) {
            return OrderMenu.builder()
                    .menuName(menu.getMenuName())
                    .price(menu.getPrice())
                    .build();
        }

        return OrderMenu.builder()
                .menuName(menu.getMenuName())
                .optionDescription(menuOption.getDescription())
                .price(menu.getPrice() + menuOption.getAdditionalPrice())
                .build();
    }

}
