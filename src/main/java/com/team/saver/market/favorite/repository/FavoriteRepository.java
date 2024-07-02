package com.team.saver.market.favorite.repository;

import com.team.saver.market.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , CustomFavoriteRepository {
}
