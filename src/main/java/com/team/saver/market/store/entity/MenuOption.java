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

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int optionPrice;

    @Column(nullable = false)
    private boolean isAdultMenu;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MenuOptionContainer menuOptionContainer;

    public static MenuOption createEntity(MenuOptionCreateRequest request) {
        return MenuOption.builder()
                .description(request.getDescription())
                .optionPrice(request.getOptionPrice())
                .isAdultMenu(request.isAdultMenu())
                .build();
    }

}
