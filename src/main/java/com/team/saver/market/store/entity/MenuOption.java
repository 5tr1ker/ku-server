package com.team.saver.market.store.entity;

import com.team.saver.market.store.dto.MenuOptionCreateRequest;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MenuOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuOptionId;

    private String description;

    private int optionPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Menu menu;

    public static MenuOption createEntity(MenuOptionCreateRequest request) {
        return MenuOption.builder()
                .description(request.getDescription())
                .optionPrice(request.getOptionPrice())
                .build();
    }

}
