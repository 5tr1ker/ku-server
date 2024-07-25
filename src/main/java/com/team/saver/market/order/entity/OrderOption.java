package com.team.saver.market.order.entity;

import com.team.saver.market.store.entity.MenuOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OrderOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderOptionId;

    private String optionDescription;

    private long optionPrice;

    @Setter
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderMenu orderMenu;

    public static OrderOption createEntity(MenuOption menuOption) {
        return OrderOption.builder()
                .optionDescription(menuOption.getDescription())
                .optionPrice(menuOption.getOptionPrice())
                .build();
    }

}
