package com.team.saver.market.store.repository;

import com.team.saver.market.store.dto.StoreResponse;
import com.team.saver.market.store.entity.MainCategory;

import java.util.List;

public interface CustomMarketRepository {

    List<StoreResponse> findMarkets();

    List<StoreResponse> findMarketsByMainCategory(MainCategory category);

    List<StoreResponse> findMarketsByMarketNameContaining(String marketName);

}
