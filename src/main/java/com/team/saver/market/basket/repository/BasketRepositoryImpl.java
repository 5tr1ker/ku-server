package com.team.saver.market.basket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.basket.entity.Basket;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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
}
