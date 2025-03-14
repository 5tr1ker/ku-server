package com.team.saver.market.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderMenuResponse;
import com.team.saver.market.order.dto.OrderOptionResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.entity.Order;
import com.team.saver.market.order.entity.OrderMenu;
import com.team.saver.market.order.entity.QOrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.market.order.entity.QOrderOption.orderOption;
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
    public List<OrderResponse> findOrderDataByUserEmail(String email, boolean existReview, Pageable pageable) {
        JPAQuery<Long> query = jpaQueryFactory.select(order.orderId)
                .from(order)
                .innerJoin(order.account, account).on(account.email.eq(email))
                .orderBy(order.orderId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        if (existReview) {
            query.innerJoin(order.review);
        } else {
            query.leftJoin(order.review, review);
            query.where(review.isNull());
        }

        List<Long> indexKey = query.fetch();

        System.out.println("email : " + email);
        System.out.println(indexKey);

        return jpaQueryFactory.select(
                        Projections.constructor(
                                OrderResponse.class,
                                order.orderId,
                                orderDetail.orderDateTime,
                                market.marketName,
                                market.marketImage,
                                orderDetail.finalPrice,
                                orderMenu.count(),
                                orderMenu.menuName
                        )
                ).from(order)
                .innerJoin(order.orderDetail, orderDetail)
                .innerJoin(order.market, market)
                .innerJoin(order.orderMenus, orderMenu)
                .groupBy(order.orderId)
                .orderBy(order.orderId.desc())
                .where(order.orderId.in(indexKey))
                .fetch();
    }

    @Override
    public Optional<OrderDetailResponse> findOrderDetailByOrderIdAndEmail(long orderId, String email) {
        QOrderMenu qOrderMenu = new QOrderMenu("orderMenu_2");

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
                                list(Projections.constructor(
                                                OrderMenuResponse.class,
                                                orderMenu.orderMenuId,
                                                orderMenu.menuName,
                                                orderMenu.price,
                                                orderMenu.amount
                                        )
                                )
                        )
                ));

        if (result.size() == 0) {
            return Optional.empty();
        }

        for (OrderMenuResponse orderMenuResponse : result.get(0).getOrderMenus()) {
            orderMenuResponse.setOptions(
                    jpaQueryFactory.select(
                                    Projections.constructor(
                                            OrderOptionResponse.class,
                                            orderOption.optionDescription,
                                            orderOption.optionPrice
                                    )
                            ).from(orderOption)
                            .innerJoin(orderOption.orderMenu, orderMenu).on(orderMenu.orderMenuId.eq(orderMenuResponse.getOrderMenuId()))
                            .fetch()
            );
        }

        return Optional.ofNullable(result.get(0));
    }

}
