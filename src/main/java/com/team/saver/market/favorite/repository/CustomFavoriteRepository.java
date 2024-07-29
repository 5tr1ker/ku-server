package com.team.saver.market.favorite.repository;

import com.team.saver.market.favorite.entity.Favorite;
import com.team.saver.market.store.dto.MarketResponse;

import java.util.List;
import java.util.Optional;

public interface CustomFavoriteRepository {

    Optional<Favorite> findByUserEmailAndMarketId(String userEmail, long marketId);

    List<MarketResponse> findFavoriteMarketByUserEmail(String email);

    List<Favorite> findByUserEmailAndIds(String email, List<Long> ids);

    long findFavoriteMarketCountByUserEmail(String email);
}
