package com.team.saver.favorite.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.favorite.entity.Favorite;

import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.favorite.entity.QFavorite.favorite;
import static com.team.saver.market.store.entity.QMarket.market;
import java.util.Optional;

@RequiredArgsConstructor
public class FavoriteRepositoryImpl implements CustomFavoriteRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Favorite> findByUserEmailAndMarketId(String userEmail, long marketId) {
        Favorite result = jpaQueryFactory.select(favorite)
                .from(favorite)
                .innerJoin(favorite.market, market).on(market.marketId.eq(marketId))
                .innerJoin(favorite.account, account).on(account.email.eq(userEmail))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
