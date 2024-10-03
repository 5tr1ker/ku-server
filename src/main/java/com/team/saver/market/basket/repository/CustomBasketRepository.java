package com.team.saver.market.basket.repository;

import com.team.saver.common.dto.NoOffset;
import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.store.entity.Market;

import java.util.List;
import java.util.Optional;

public interface CustomBasketRepository {

    Optional<Basket> findByMarketIdAndAccountEmail(long marketId, String email);

    List<BasketResponse> findAllByAccountEmail(String email, NoOffset noOffset);

    List<BasketResponse> findByIdAndAccountEmail(String email, List<Long> ids);

    Long findBasketMenuCountByAccountEmail(String email);

    List<Basket> findByEmailAndMarketIdNot(long marketId, String email);

    List<Basket> findAllByEmail(String email);

}
