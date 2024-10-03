package com.team.saver.market.basket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.market.basket.dto.BasketMenuResponse;
import com.team.saver.market.basket.dto.BasketOptionResponse;
import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.basket.entity.QBasketMenu;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.basket.entity.QBasket.basket;
import static com.team.saver.market.basket.entity.QBasketMenu.basketMenu;
import static com.team.saver.market.basket.entity.QBasketMenuOption.basketMenuOption;
import static com.team.saver.market.store.entity.QMarket.market;
import static com.team.saver.market.store.entity.QMenu.menu;
import static com.team.saver.market.store.entity.QMenuOption.menuOption;

@RequiredArgsConstructor
public class BasketRepositoryImpl implements CustomBasketRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Basket> findByMarketIdAndAccountEmail(long marketId, String email) {
        Basket result = jpaQueryFactory.select(basket)
                .from(basket)
                .innerJoin(basket.market, market).on(market.marketId.eq(marketId))
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<BasketResponse> findAllByAccountEmail(String email, NoOffset noOffset) {
        QBasketMenu qBasketMenu = new QBasketMenu("qBasketMenu");

        JPAQuery<Long> query = jpaQueryFactory.select(basket.basketId)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .orderBy(basket.updateTime.desc())
                .limit(noOffset.getSize());

        if(noOffset.getLastIndex() != 0) {
            query.where(basket.updateTime.before(
                    select(basket.updateTime)
                            .from(basket)
                            .where(basket.basketId.eq(noOffset.getLastIndex()))
            ));
        }

        List<Long> ids = query.fetch();


        List<BasketResponse> result = jpaQueryFactory.selectFrom(basket)
                .innerJoin(basket.market, market)
                .innerJoin(basket.basketMenus, basketMenu)
                .innerJoin(basketMenu.menu, menu)
                .orderBy(basket.updateTime.desc())
                .where(basket.basketId.in(ids))
                .transform(groupBy(basket.basketId).list(
                        Projections.constructor(
                                BasketResponse.class,
                                basket.basketId,
                                market.marketId,
                                market.marketName,
                                list(Projections.constructor(
                                        BasketMenuResponse.class,
                                        basketMenu.basketMenuId,
                                        menu.menuName,
                                        menu.imageUrl,
                                        menu.price,
                                        basketMenu.amount,
                                        select(menuOption.optionPrice.sum()).from(basketMenuOption)
                                                .innerJoin(basketMenuOption.menuOption, menuOption)
                                                .innerJoin(basketMenuOption.basketMenu, qBasketMenu).on(qBasketMenu.eq(basketMenu))
                                ))
                        )
                ));

        for (BasketResponse basketResponse : result) {
            for (BasketMenuResponse basketMenuResponse : basketResponse.getMenu()) {
                basketMenuResponse.setOptions(
                        jpaQueryFactory.select(Projections.constructor(
                                        BasketOptionResponse.class,
                                        menuOption.description,
                                        menuOption.optionPrice
                                ))
                                .from(basketMenuOption)
                                .innerJoin(basketMenuOption.menuOption, menuOption)
                                .innerJoin(basketMenuOption.basketMenu, basketMenu).on(basketMenu.basketMenuId.eq(basketMenuResponse.getBasketMenuId()))
                                .fetch()
                );

                basketMenuResponse.calculateTotalPrice();
            }

            basketResponse.calculateTotalPrice();
        }

        return result;
    }

    @Override
    public List<BasketResponse> findByIdAndAccountEmail(String email, List<Long> ids) {
        QBasketMenu qBasketMenu = new QBasketMenu("qBasketMenu");

        List<BasketResponse> result = jpaQueryFactory.selectFrom(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.market, market)
                .innerJoin(basket.basketMenus, basketMenu).on(basketMenu.basketMenuId.in(ids))
                .innerJoin(basketMenu.menu, menu)
                .orderBy(basket.updateTime.desc())
                .transform(groupBy(basket.basketId).list(
                        Projections.constructor(
                                BasketResponse.class,
                                basket.basketId,
                                market.marketId,
                                market.marketName,
                                list(Projections.constructor(
                                        BasketMenuResponse.class,
                                        basketMenu.basketMenuId,
                                        menu.menuName,
                                        menu.imageUrl,
                                        menu.price,
                                        basketMenu.amount,
                                        select(menuOption.optionPrice.sum()).from(basketMenuOption)
                                                .innerJoin(basketMenuOption.menuOption, menuOption)
                                                .innerJoin(basketMenuOption.basketMenu, qBasketMenu).on(qBasketMenu.eq(basketMenu))
                                ))
                        )
                ));

        for (BasketResponse basketResponse : result) {
            for (BasketMenuResponse basketMenuResponse : basketResponse.getMenu()) {
                basketMenuResponse.setOptions(
                        jpaQueryFactory.select(Projections.constructor(
                                        BasketOptionResponse.class,
                                        menuOption.description,
                                        menuOption.optionPrice
                                ))
                                .from(basketMenuOption)
                                .innerJoin(basketMenuOption.menuOption, menuOption)
                                .innerJoin(basketMenuOption.basketMenu, basketMenu).on(basketMenu.basketMenuId.eq(basketMenuResponse.getBasketMenuId()))
                                .fetch()
                );

                basketMenuResponse.calculateTotalPrice();
            }

            basketResponse.calculateTotalPrice();
        }

        return result;
    }

    @Override
    public Long findBasketMenuCountByAccountEmail(String email) {
        return jpaQueryFactory.select(basketMenu.count())
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .leftJoin(basket.basketMenus, basketMenu)
                .fetchOne();
    }

    @Override
    public List<Basket> findByEmailAndMarketIdNot(long marketId, String email) {
        return jpaQueryFactory.select(basket)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.basketMenus)
                .where(basket.basketId.ne(marketId))
                .fetch();
    }

    @Override
    public List<Basket> findAllByEmail(String email) {
        return jpaQueryFactory.select(basket)
                .from(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .fetch();
    }

}
