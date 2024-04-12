package com.team.saver.market.store.repository;

import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.MainCategory;

import java.util.List;

public interface CustomMarketRepository {

    List<MarketResponse> findMarkets();

    List<MarketResponse> findMarketsByMainCategory(MainCategory category);

    List<MarketResponse> findMarketsByMarketNameContaining(String marketName);

}
