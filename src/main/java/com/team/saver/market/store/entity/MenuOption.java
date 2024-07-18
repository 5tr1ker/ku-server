package com.team.saver.market.store.entity;

import com.team.saver.market.store.dto.MenuOptionCreateRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MenuOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuOptionId;

    private String description;

    private int additionalPrice;

    public static MenuOption createEntity(MenuOptionCreateRequest request) {
        return MenuOption.builder()
                .description(request.getDescription())
                .additionalPrice(request.getAdditionalPrice())
                .build();
    }

}
