package com.team.saver.market.basket.service;

import com.team.saver.market.basket.repository.BasketMenuRepository;
import com.team.saver.market.basket.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketMenuRepository basketMenuRepository;

}
