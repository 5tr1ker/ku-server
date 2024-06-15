package com.team.saver.market.order.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    @OneToMany
    private List<OrderMenu> orderMenuName;

    @OneToOne
    private OrderDetail orderDetail;

}
