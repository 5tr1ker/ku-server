package com.team.saver.market.basket.entity;

import com.team.saver.market.store.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasketMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long basketMenuId;

    @OneToOne(fetch = FetchType.LAZY)
    private Menu menu;

    private long amount;

}
