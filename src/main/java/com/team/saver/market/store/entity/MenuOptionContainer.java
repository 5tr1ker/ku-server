package com.team.saver.market.store.entity;

import com.team.saver.market.store.dto.MenuOptionContainerCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MenuOptionContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long menuOptionContainerId;

    @Column(nullable = false)
    private String classification;

    @Column(nullable = false)
    private long priority;

    @Column(nullable = false)
    private boolean isMultipleSelection;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "menuOptionContainer")
    private List<MenuOption> menuOptions = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Menu menu;

    public void addMenuOption(MenuOption menuOption) {
        this.menuOptions.add(menuOption);

        menuOption.setMenuOptionContainer(this);
    }

    public static MenuOptionContainer createEntity(MenuOptionContainerCreateRequest request, long priority) {
        return MenuOptionContainer.builder()
                .classification(request.getClassification())
                .priority(priority)
                .isMultipleSelection(request.isMultipleSelection())
                .build();
    }

}
