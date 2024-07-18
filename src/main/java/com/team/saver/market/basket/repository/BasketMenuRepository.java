package com.team.saver.market.basket.repository;

import com.team.saver.market.basket.entity.BasketMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketMenuRepository extends JpaRepository<BasketMenu, Long>, CustomBasketMenuRepository {

    long deleteByBasketMenuId(long basketMenuId);

}
