package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.entity.Market;

import java.util.List;

public interface CustomMarketRepository {

    List<Market> findMarketsByMainCategory(MainCategory category);

    List<Market> findMarketsByMarketNameContaining(String marketName);

}
