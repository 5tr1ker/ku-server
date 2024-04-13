package com.team.saver.market.store.repository;

import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;

import java.util.List;
import java.util.Optional;

public interface CustomMarketRepository {

    List<MarketResponse> findMarkets();

    List<MarketResponse> findMarketsByMainCategory(MainCategory category);

    List<MarketResponse> findMarketsByMainCategoryAndMarketName(MainCategory category, String marketName);

    List<MarketResponse> findMarketsByMarketName(String marketName);

    Optional<Market> findMarketDetailById(long marketId);

    Optional<Market> findMarketByMarketIdAndPartnerEmail(String partnerEmail, long marketId);

}
