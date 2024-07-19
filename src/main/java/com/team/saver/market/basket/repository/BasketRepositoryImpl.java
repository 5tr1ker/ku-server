package com.team.saver.market.basket.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.basket.dto.BasketMenuResponse;
import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.store.entity.QMenu;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.team.saver.market.store.entity.QMenuOption.menuOption;
import static com.team.saver.market.store.entity.QMenu.menu;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.team.saver.market.basket.entity.QBasketMenu.basketMenu;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.basket.entity.QBasket.basket;
import static com.team.saver.market.store.entity.QMarket.market;

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
    public List<BasketResponse> findAllByAccountEmail(String email) {
        return jpaQueryFactory.selectFrom(basket)
                .innerJoin(basket.account, account).on(account.email.eq(email))
                .innerJoin(basket.market, market)
                .innerJoin(basket.basketMenus, basketMenu)
                .leftJoin(basketMenu.menuOption, menuOption)
                .innerJoin(basketMenu.menu, menu)
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
                                        menuOption.description,
                                        menuOption.additionalPrice,
                                        menu.price.add(menuOption.additionalPrice).multiply(basketMenu.amount)
                                ))
                        )
                ));
    }

    @Override
    public List<BasketResponse> findByIdAndAccountEmail(String email, List<Long> ids) {
        return null;
    }

}
