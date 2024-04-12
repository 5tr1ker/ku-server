package com.team.saver.market.store.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MarketRepositoryImpl implements CustomMarketRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Market> findMarketsByMainCategory(MainCategory category) {
        return null;
    }

    @Override
    public List<Market> findMarketsByMarketNameContaining(String marketName) {
        return null;
    }
}
