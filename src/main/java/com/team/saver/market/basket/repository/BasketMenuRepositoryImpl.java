package com.team.saver.market.basket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.store.entity.MenuOption;
import lombok.RequiredArgsConstructor;

import static com.querydsl.jpa.JPAExpressions.select;
import com.team.saver.market.basket.entity.QBasketMenu;
import static com.team.saver.market.basket.entity.QBasketMenuOption.basketMenuOption;
import static com.team.saver.market.store.entity.QMenuOption.menuOption;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.basket.entity.QBasket.basket;
import static com.team.saver.market.basket.entity.QBasketMenu.basketMenu;

import java.util.List;
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
    public long deleteByBasketMenuIds(String email, List<Long> ids) {
        List<Long> basketMenuId = jpaQueryFactory
                .select(basketMenu.basketMenuId)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.basketMenus, basketMenu).on(basketMenu.basketMenuId.in(ids))
                .fetch();

        return jpaQueryFactory.delete(basketMenu)
                .where(basketMenu.basketMenuId.in(basketMenuId))
                .execute();
    }

    @Override
    public List<BasketMenu> findAllByAccountEmailAndId(String email, List<Long> basketMenuId) {
        return jpaQueryFactory.select(basketMenu)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.basketMenus, basketMenu).on(basketMenu.basketMenuId.in(basketMenuId))
                .fetch();
    }

    @Override
    public List<MenuOption> findMenuOptionById(long basketMenuId) {
        return jpaQueryFactory.select(menuOption)
                .from(basketMenuOption)
                .innerJoin(basketMenuOption.basketMenu, basketMenu).on(basketMenu.basketMenuId.eq(basketMenuId))
                .innerJoin(basketMenuOption.menuOption, menuOption)
                .fetch();
    }

}
