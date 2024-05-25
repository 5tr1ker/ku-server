package com.team.saver.market.store.controller;

import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.service.MarketService;
import com.team.saver.market.store.util.RecommendAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;
    private final RecommendAlgorithm recommendAlgorithm;

    @GetMapping("/{marketId}")
    @Operation(summary = "해당 Market의 상세 정보 가져오기")
    public ResponseEntity findMarketDetailById(@PathVariable long marketId) {
        MarketDetailResponse result = marketService.findMarketDetailById(marketId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/recommend")
    @Operation(summary = "Market 추천 받기")
    public ResponseEntity recommendMarket(@RequestBody MarketRecommendRequest request) {
        List<MarketResponse> result = recommendAlgorithm.recommendMarket(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/search")
    @Operation(summary = "모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ] ")
    public ResponseEntity findAllMarket(@RequestBody SearchMarketRequest request) {
        List<MarketResponse> result = marketService.findAllMarket(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/search/market-name")
    @Operation(summary = "Market 이름이 포함되어 있는 모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ]")
    public ResponseEntity findMarketBySearch(@RequestBody SearchByNameRequest request) {
        List<MarketResponse> result = marketService.findMarketBySearch(request);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/search/category")
    @Operation(summary = "해당 category 에 해당되는 모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ]")
    public ResponseEntity findMarketByMainCategory(@RequestBody SearchByCategoryRequest request) {
        List<MarketResponse> result = marketService.findMarketByMainCategory(request);

        return ResponseEntity.ok(result);
    }

}
