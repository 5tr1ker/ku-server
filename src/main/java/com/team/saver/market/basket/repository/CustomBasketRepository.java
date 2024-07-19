package com.team.saver.market.basket.repository;

import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.entity.Basket;

import java.util.List;
import java.util.Optional;

public interface CustomBasketRepository {

    Optional<Basket> findByMarketIdAndAccountEmail(long marketId, String email);

    List<BasketResponse> findAllByAccountEmail(String email);

    List<BasketResponse> findByIdAndAccountEmail(String email, List<Long> ids);

}
