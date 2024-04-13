package com.team.saver.market.store.service;

import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_MARKET;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    public List<MarketResponse> findAllMarket(SearchMarketRequest request) {
        List<MarketResponse> result = marketRepository.findMarkets();

        return MarketSortTool.sortMarket(result, request.getSort(), request.getDistance());
    }

    public List<MarketResponse> findMarketBySearch(SearchByNameRequest request) {
        List<MarketResponse> result = marketRepository.findMarketsByMarketName(request.getMarketName());

        return MarketSortTool.sortMarket(result, request.getSort(), request.getDistance());
    }

    public List<MarketResponse> findMarketByMainCategory(SearchByCategoryRequest request) {
        List<MarketResponse> result;

        if(request.getMarketName() == null) {
            result = marketRepository.findMarketsByMainCategory(request.getCategory());
        } else {
            result = marketRepository.findMarketsByMainCategoryAndMarketName(request.getCategory(), request.getMarketName());
        }

        return MarketSortTool.sortMarket(result, request.getSort(), request.getDistance());
    }

    public MarketDetailResponse findMarketDetailById(long marketId) {
        Market market = marketRepository.findMarketDetailById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        return MarketDetailResponse.createResponse(market);
    }

}
