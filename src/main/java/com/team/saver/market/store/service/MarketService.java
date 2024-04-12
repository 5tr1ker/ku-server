package com.team.saver.market.store.service;

import com.team.saver.market.store.dto.SearchByCategoryRequest;
import com.team.saver.market.store.dto.SearchByNameRequest;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.dto.SearchMarketRequest;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
