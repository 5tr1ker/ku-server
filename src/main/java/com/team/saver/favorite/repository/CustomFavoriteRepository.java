package com.team.saver.favorite.repository;

import com.team.saver.favorite.entity.Favorite;

import java.util.Optional;

public interface CustomFavoriteRepository {

    Optional<Favorite> findByUserEmailAndMarketId(String userEmail, long marketId);

}
