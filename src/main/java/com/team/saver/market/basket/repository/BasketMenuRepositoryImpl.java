package com.team.saver.market.basket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.basket.entity.QBasketMenu;
import lombok.RequiredArgsConstructor;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.basket.entity.QBasket.basket;
import static com.team.saver.market.basket.entity.QBasketMenu.basketMenu;

import java.util.Optional;

@RequiredArgsConstructor
public class BasketMenuRepositoryImpl implements CustomBasketMenuRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BasketMenu> findByAccountEmailAndId(String email, long basketMenuId) {
        BasketMenu result = jpaQueryFactory.select(basketMenu)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.basketMenus, basketMenu).on(basketMenu.basketMenuId.eq(basketMenuId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public long deleteByAccountEmailAndBasketMenuId(String email, long basketMenuId) {
        QBasketMenu basketMenu2 = new QBasketMenu("basketMenu2");

        return jpaQueryFactory.delete(basketMenu)
                .where(basketMenu.basketMenuId.eq(
                                select(basketMenu.basketMenuId)
                                        .from(basket)
                                        .innerJoin(basket.account, account).on(account.email.eq(email))
                                        .innerJoin(basket.basketMenus, basketMenu2).on(basketMenu2.basketMenuId.eq(basketMenuId))
                                        .fetchOne()
                        )
                ).execute();
    }
}
