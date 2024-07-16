package com.team.saver.market.basket.repository;

import com.team.saver.market.basket.entity.BasketMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<BasketMenu, Long> {
}
