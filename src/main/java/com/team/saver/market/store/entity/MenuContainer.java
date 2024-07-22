package com.team.saver.market.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MenuContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuContainerId;

    @Column(nullable = false)
    private long priority;

    @Column(nullable = false)
    private String classification;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "menuContainer")
    private List<Menu> menus = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Market market;

    public void addMenu(Menu menu) {
        this.menus.add(menu);

        menu.setMenuContainer(this);
    }

    public static MenuContainer createEntity(String classification, long priority) {
        return MenuContainer.builder()
                .classification(classification)
                .priority(priority)
                .build();
    }

}
