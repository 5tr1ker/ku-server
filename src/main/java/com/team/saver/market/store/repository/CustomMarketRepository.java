package com.team.saver.market.store.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.team.saver.market.store.dto.MarketDetailResponse;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.dto.MenuResponse;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;

import java.util.List;
import java.util.Optional;

public interface CustomMarketRepository {

    List<MarketResponse> findMarkets();

    List<MarketResponse> findMarketsByConditional(BooleanExpression conditional);

    List<MarketResponse> findMarketsAndSort(OrderSpecifier orderSpecifier, BooleanExpression conditional);

    Optional<MarketDetailResponse> findMarketDetailById(long marketId);

    Optional<Market> findMarketByMarketIdAndPartnerEmail(String partnerEmail, long marketId);

    Optional<Market> findMarketAndMenuByMarketId(long marketId);

    List<MenuResponse> findMarketMenuById(long marketId);
    
}
