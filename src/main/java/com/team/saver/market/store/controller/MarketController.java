package com.team.saver.market.store.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.service.MarketService;
import com.team.saver.market.store.util.RecommendAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;
    private final RecommendAlgorithm recommendAlgorithm;

    @PostMapping("/v1/markets")
    @Operation(summary = "[ 로그인 ] Market 데이터 추가")
    public ResponseEntity addMarket(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                    @RequestBody MarketCreateRequest request) {
        marketService.addMarket(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/markets/{marketId}/details")
    @Operation(summary = "해당 Market의 상세 정보 가져오기")
    public ResponseEntity findMarketDetailById(@PathVariable long marketId) {
        MarketDetailResponse result = marketService.findMarketDetailById(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketId}/menus")
    @Operation(summary = "해당 Market의 메뉴 정보 가져오기")
    public ResponseEntity findMarketMenuById(@PathVariable long marketId) {
        List<MenuResponse> result = marketService.findMarketMenuById(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/recommendation")
    @Operation(summary = "Market 추천 알고리즘으로 추천 받기")
    public ResponseEntity recommendMarket(@RequestParam long marketCount) {
        List<MarketResponse> result = recommendAlgorithm.recommendMarket(marketCount);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets")
    @Operation(summary = "모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ] ")
    public ResponseEntity findAllMarket(MarketSearchRequest request, Pageable pageable) {
        List<MarketResponse> result = marketService.findAllMarket(request, pageable);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/v1/markets/{marketId}/event-message")
    @Operation(summary = "[ 로그인 ] Market의 이벤트 메세지 변경")
    public ResponseEntity modifyEventMessage(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                             @PathVariable long marketId,
                                             @RequestBody MarketEventUpdateRequest request) {
        marketService.modifyEventMessage(request, currentUser, marketId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/markets/{marketName}")
    @Operation(summary = "Market 이름이 포함되어 있는 모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ]")
    public ResponseEntity findMarketBySearch(MarketSearchRequest request,
                                             @PathVariable String marketName,
                                             Pageable pageable) {
        List<MarketResponse> result = marketService.findMarketByMarketName(request, marketName, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/categories/{categoryData}")
    @Operation(summary = "category 에 해당되는 모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ]")
    public ResponseEntity findMarketByMainCategory(MarketSearchRequest request,
                                                   @PathVariable MainCategory categoryData,
                                                   Pageable pageable) {
        List<MarketResponse> result = marketService.findMarketByMainCategory(request, categoryData, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketName}/categories/{categoryData}")
    @Operation(summary = "category 와 Market 이름 에 해당되는 모든 Market 정보 가져오기 [ distance 는 sort가 DISTANCE일때만 넣어주세요. ]")
    public ResponseEntity findMarketByMainCategoryAndMarketName(MarketSearchRequest request,
                                                                @PathVariable MainCategory categoryData,
                                                                @PathVariable String marketName,
                                                                Pageable pageable) {
        List<MarketResponse> result = marketService.findMarketByMainCategoryAndMarketName(request, categoryData, marketName, pageable);

        return ResponseEntity.ok(result);
    }

}
