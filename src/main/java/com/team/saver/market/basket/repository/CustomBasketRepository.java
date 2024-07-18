package com.team.saver.market.basket.repository;

import com.team.saver.market.basket.entity.Basket;

import java.util.Optional;

public interface CustomBasketRepository {

    Optional<Basket> findByMarketIdAndAccountEmail(long marketId, String email);

}
