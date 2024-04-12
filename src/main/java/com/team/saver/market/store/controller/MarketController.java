package com.team.saver.market.store.controller;

import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.dto.SearchByCategoryRequest;
import com.team.saver.market.store.dto.SearchByNameRequest;
import com.team.saver.market.store.dto.SearchMarketRequest;
import com.team.saver.market.store.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/search")
    public ResponseEntity findAllMarket(@RequestBody SearchMarketRequest request) {
        List<MarketResponse> result = marketService.findAllMarket(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/market-name")
    public ResponseEntity findMarketBySearch(@RequestBody SearchByNameRequest request) {
        List<MarketResponse> result = marketService.findMarketBySearch(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/search/category")
    public ResponseEntity findMarketByMainCategory(@RequestBody SearchByCategoryRequest request) {
        List<MarketResponse> result = marketService.findMarketByMainCategory(request);

        return ResponseEntity.ok(result);
    }

}
