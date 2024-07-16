package com.team.saver.market.basket.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.store.entity.Market;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Basket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long basketId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    @Builder.Default
    @OneToMany(cascade = { CascadeType.PERSIST , CascadeType.REMOVE }, orphanRemoval = true)
    private List<BasketMenu> basketMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
