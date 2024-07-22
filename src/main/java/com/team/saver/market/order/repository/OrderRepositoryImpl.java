package com.team.saver.market.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderMenuResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderMenu;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.order.entity.QOrder.order;
import static com.team.saver.market.order.entity.QOrderDetail.orderDetail;
import static com.team.saver.market.order.entity.QOrderMenu.orderMenu;
import static com.team.saver.market.store.entity.QMarket.market;

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
    public List<OrderResponse> findOrderDataByUserEmail(String email, boolean existReview) {
        JPAQuery<Order> result = jpaQueryFactory.selectFrom(order)
                .innerJoin(order.account, account).on(account.email.eq(email))
                .innerJoin(order.orderDetail, orderDetail)
                .innerJoin(order.market, market)
                .innerJoin(order.orderMenus, orderMenu);

        if (existReview) {
            result.innerJoin(order.review);
        } else {
            result.leftJoin(order.review, review);
            result.where(review.isNull());
        }

        return result.transform(groupBy(order.orderId)
                .list(Projections.constructor(
                        OrderResponse.class,
                        order.orderId,
                        orderDetail.orderDateTime,
                        market.marketName,
                        list(Projections.constructor(
                                OrderMenuResponse.class,
                                orderMenu.orderMenuId,
                                orderMenu.menuName,
                                orderMenu.price
                        ))
                )));
    }

    @Override
    public Optional<OrderDetailResponse> findOrderDetailByOrderIdAndEmail(long orderId, String email) {
        List<OrderDetailResponse> result = jpaQueryFactory.selectFrom(order)
                .innerJoin(order.account, account).on(account.email.eq(email))
                .innerJoin(order.orderDetail, orderDetail)
                .innerJoin(order.market, market)
                .innerJoin(order.orderMenus, orderMenu)
                .where(order.orderId.eq(orderId))
                .transform(groupBy(order.orderId).list(
                        Projections.constructor(
                                OrderDetailResponse.class,
                                order.orderId,
                                market.marketId,
                                market.marketName,
                                market.marketPhone,
                                market.detailAddress,
                                orderDetail.orderDateTime,
                                orderDetail.orderNumber,
                                orderDetail.orderPrice,
                                orderDetail.discountAmount,
                                orderDetail.paymentType,
                                orderDetail.finalPrice,
                                list(
                                        Projections.constructor(OrderMenuResponse.class,
                                                orderMenu.orderMenuId,
                                                orderMenu.menuName,
                                                orderMenu.optionDescription,
                                                orderMenu.price
                                        )
                                )
                        )
                ));

        if(result.size() == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(result.get(0));
    }

}
