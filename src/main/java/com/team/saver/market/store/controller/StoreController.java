package com.team.saver.market.store.controller;

import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final MarketRepository repository;

    @GetMapping("/awd")
    public Object a() {
        return repository.findMarkets();
    }
}
