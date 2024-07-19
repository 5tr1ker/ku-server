package com.team.saver.market.order.entity;

import com.team.saver.market.store.entity.Menu;
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

    private int price;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Order order;

    public static OrderMenu createEntity(Menu menu) {
        return OrderMenu.builder()
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .build();
    }

}
