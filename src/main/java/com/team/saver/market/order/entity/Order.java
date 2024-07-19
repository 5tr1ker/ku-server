package com.team.saver.market.order.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_tb")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Market market;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private Review review;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, mappedBy = "order")
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private OrderDetail orderDetail;

    public static Order createEntity(Market market, Account account) {
        return Order.builder()
                .market(market)
                .account(account)
                .build();
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenuList.add(orderMenu);

        orderMenu.setOrder(this);
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
        orderDetail.setOrder(this);
    }
}
