package com.team.saver.favorite.repository;

import com.team.saver.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> , CustomFavoriteRepository {
}
