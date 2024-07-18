package com.team.saver.market.store.entity;

import com.team.saver.market.store.dto.MenuCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private String description;

    private String imageUrl;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true)
    List<MenuOption> menuOptions = new ArrayList<>();

    public void addMenuOption(MenuOption menuOption) {
        this.menuOptions.add(menuOption);
    }

    public static Menu createEntity(MenuCreateRequest request, String imageUrl) {
        return Menu.builder()
                .price(request.getPrice())
                .description(request.getDescription())
                .menuName(request.getMenuName())
                .imageUrl(imageUrl)
                .build();
    }

}
