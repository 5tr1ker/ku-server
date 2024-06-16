package com.team.saver.market.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import lombok.RequiredArgsConstructor;

import static com.team.saver.market.order.entity.QOrderMenu.orderMenu;
import static com.team.saver.market.store.entity.QMenu.menu;
import static com.team.saver.market.store.entity.QMarket.market;
import static com.team.saver.market.order.entity.QOrderDetail.orderDetail;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.order.entity.QOrder.order;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements CustomOrderRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Order> findByIdAndUserEmail(String email, long orderId) {
        Order result = jpaQueryFactory.select(order)
                .from(order)
                .innerJoin(order.account, account).on(account.email.eq(email))
                .where(order.orderId.eq(orderId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<OrderResponse> findOrderByUserEmail(String email) {
        return jpaQueryFactory.select(Projections.constructor(OrderResponse.class,
                        order.orderId,
                        orderDetail.orderDateTime,
                        market.marketName,
                        orderMenu
                ))
                .innerJoin(order.account, account).on(account.email.eq(email))
                .innerJoin(order.orderDetail, orderDetail)
                .innerJoin(order.market, market)
                .innerJoin(order.orderMenuList, orderMenu)
                .from(order)
                .fetch();
    }

    @Override
    public Optional<OrderDetailResponse> getOrderDetailByOrderIdAndEmail(long orderId, String email) {
        OrderDetailResponse result = jpaQueryFactory.select(Projections.constructor(
                        OrderDetailResponse.class,
                        order.orderId,
                        market.marketName,
                        market.marketId,
                        orderMenu,
                        orderDetail.orderDateTime,
                        orderDetail.orderNumber,
                        orderDetail.deliveryAddress,
                        orderDetail.deliveryAddressDetail,
                        orderDetail.phoneNumber,
                        orderDetail.orderPrice,
                        orderDetail.discountAmount,
                        orderDetail.finalPrice
                ))
                .from(order)
                .innerJoin(order.account, account).on(account.email.eq(email))
                .innerJoin(order.orderDetail, orderDetail)
                .innerJoin(order.market, market)
                .innerJoin(order.orderMenuList, orderMenu)
                .where(order.orderId.eq(orderId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
