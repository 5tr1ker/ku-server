package com.team.saver.market.store.util;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.common.util.DistanceCalculator;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.dto.MarketSearchRequest;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_SORT_TYPE;
import static com.team.saver.market.store.util.SortType.*;

@Component
@RequiredArgsConstructor
public class MarketSortTool {

    private final MarketRepository marketRepository;

    public List<MarketResponse> sortMarket(MarketSearchRequest request, BooleanExpression conditional, Pageable pageable) {
        SortType sort = request.getSort();

        if (sort.equals(SortType.NEAR_DISTANCE)) {
            List<MarketResponse> marketResponse = marketRepository.findMarketsByConditional(conditional, pageable);

            return sortByDistance(marketResponse, request.getLocationX(), request.getLocationY());
        } else if (sort.equals(HIGHEST_DISCOUNT)) {
            return findMarketAndCalculateDistance(request, HIGHEST_DISCOUNT.getOrderSpecifier(), conditional, pageable);
        } else if (sort.equals(HIGHEST_REVIEW_RATED)) {
            return findMarketAndCalculateDistance(request, HIGHEST_REVIEW_RATED.getOrderSpecifier(), conditional, pageable);
        } else if (sort.equals(MANY_REVIEW_COUNT)) {
            return findMarketAndCalculateDistance(request, MANY_REVIEW_COUNT.getOrderSpecifier(), conditional, pageable);
        } else if (sort.equals(RECENTLY_UPLOAD)) {
            return findMarketAndCalculateDistance(request, RECENTLY_UPLOAD.getOrderSpecifier(), conditional, pageable);
        }

        throw new CustomRuntimeException(NOT_FOUND_SORT_TYPE);
    }

    private List<MarketResponse> findMarketAndCalculateDistance(MarketSearchRequest request, OrderSpecifier orderSpecifier, BooleanExpression conditional, Pageable pageable) {
        List<MarketResponse> result = marketRepository.findMarketsAndSort(orderSpecifier, conditional, pageable);

        return DistanceCalculator.calculateMarketDistance(result, request.getLocationX(), request.getLocationY());
    }

    private List<MarketResponse> sortByDistance(List<MarketResponse> marketResponse, double locationX, double locationY) {
        List<MarketResponse> storages = DistanceCalculator.calculateMarketDistance(marketResponse, locationX, locationY);

        Collections.sort(storages, new Comparator<MarketResponse>() {
            @Override
            public int compare(MarketResponse o1, MarketResponse o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        return storages;
    }


}

