package com.team.saver.market.store.entity;

import com.team.saver.market.store.dto.MenuCreateData;
import jakarta.persistence.*;
import lombok.*;

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

    @Builder.Default
    @Column(nullable = false)
    private String description = "null";

    private String imageUrl;

    @Column(nullable = false)
    private boolean isAdultMenu;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "menu")
    private List<MenuOptionContainer> menuOptionContainers = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MenuContainer menuContainer;

    @Builder.Default
    @Column(nullable = false)
    private boolean isBestMenu = false;

    @Builder.Default
    @Column(nullable = false)
    private long orderCount = 0;

    public void addMenuOptionContainer(MenuOptionContainer menuOptionContainer) {
        this.menuOptionContainers.add(menuOptionContainer);

        menuOptionContainer.setMenu(this);
    }

    public void addOrderCount() {
        this.orderCount += 1;
    }

    public void setBestMenu() {
        this.isBestMenu = true;
    }

    public static Menu createEntity(MenuCreateData request, String imageUrl) {
        return Menu.builder()
                .price(request.getPrice())
                .description(request.getDescription())
                .menuName(request.getMenuName())
                .isAdultMenu(request.isAdultMenu())
                .imageUrl(imageUrl)
                .build();
    }

}
