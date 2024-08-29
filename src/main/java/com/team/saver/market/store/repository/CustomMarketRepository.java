package com.team.saver.market.store.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomMarketRepository {

    List<MarketResponse> findMarkets();

    List<MarketResponse> findMarketsByConditional(BooleanExpression conditional, Pageable pageable);

    List<MarketResponse> findMarketsAndSort(OrderSpecifier orderSpecifier, BooleanExpression conditional, Pageable pageable);

    Optional<MarketDetailResponse> findMarketDetailById(long marketId);

    Optional<Market> findMarketByMarketIdAndPartnerEmail(String partnerEmail, long marketId);

    List<MenuClassificationResponse> findMarketMenuById(long marketId);

    List<MenuOptionClassificationResponse> findMenuOptionById(long menuId);

    Optional<MarketResponse> findByMarketName(String marketName);

    List<MarketCreateDateResponse> findMarketCreateDate(Pageable pageable);

    long findMarketCount();

    List<MarketCreateDateResponse> findMarketCreateDateByMarketName(String marketName, Pageable pageable);

}
