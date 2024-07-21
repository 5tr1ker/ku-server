package com.team.saver.market.basket.repository;

import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.basket.entity.BasketMenu;

import java.util.List;
import java.util.Optional;

public interface CustomBasketMenuRepository {

    Optional<BasketMenu> findByAccountEmailAndId(String email, long basketMenuId);

    long deleteByBasketMenuIds(String email, List<Long> ids);

    List<BasketMenu> findAllByAccountEmailAndId(String email, List<Long> basketMenuId);

}
