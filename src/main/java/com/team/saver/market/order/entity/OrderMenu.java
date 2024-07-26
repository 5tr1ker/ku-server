package com.team.saver.market.order.entity;

import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private long price;

    private long amount;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "orderMenu")
    private List<OrderOption> options = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    public void addOption(OrderOption orderOption) {
        this.options.add(orderOption);

        orderOption.setOrderMenu(this);
    }

    public static OrderMenu createEntity(BasketMenu basketMenu) {
        Menu menu = basketMenu.getMenu();

        return OrderMenu.builder()
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .amount(basketMenu.getAmount())
                .build();
    }

}
