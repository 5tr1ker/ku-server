package com.team.saver.market.store.util;

import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.store.dto.DistanceRequest;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_SORT_TYPE;

@Component
@RequiredArgsConstructor
public class MarketSortTool {

    public List<MarketResponse> sortMarket(List<MarketResponse> marketResponse, SortType sort, DistanceRequest request) {
        if (sort.equals(SortType.NEAR_DISTANCE)) {
            return sortByDistance(marketResponse, request);
        } else if (sort.equals(SortType.HIGHEST_DISCOUNT)) {
            return sortByMaxDiscountRate(marketResponse);
        } else if (sort.equals(SortType.HIGHEST_RATED)) {
            return sortByAverageReviewScore(marketResponse);
        } else if (sort.equals(SortType.MANY_REVIEW)) {
            return sortByReviewCount(marketResponse);
        } else if (sort.equals(SortType.RECENTLY_RELEASE)) {
            return sortByRecentlyRelease(marketResponse);
        }

        throw new CustomRuntimeException(NOT_FOUND_SORT_TYPE);
    }

    private static List<MarketResponse> sortByReviewCount(List<MarketResponse> marketResponse) {
        Collections.sort(marketResponse, new Comparator<MarketResponse>() {
            @Override
            public int compare(MarketResponse o1, MarketResponse o2) {
                if (o1.getReviewCount() == o2.getReviewCount()) {

                    return Double.compare(o2.getAverageReviewScore(), o1.getAverageReviewScore());
                }

                return Long.compare(o2.getReviewCount(), o1.getReviewCount());
            }
        });

        return marketResponse;
    }

    private static List<MarketResponse> sortByAverageReviewScore(List<MarketResponse> marketResponse) {
        Collections.sort(marketResponse, new Comparator<MarketResponse>() {
            @Override
            public int compare(MarketResponse o1, MarketResponse o2) {
                if (o1.getAverageReviewScore() == o2.getAverageReviewScore()) {

                    return Long.compare(o2.getReviewCount(), o1.getReviewCount());
                }

                return Double.compare(o2.getAverageReviewScore(), o1.getAverageReviewScore());
            }
        });

        return marketResponse;
    }

    private static List<MarketResponse> sortByMaxDiscountRate(List<MarketResponse> marketResponse) {
        Collections.sort(marketResponse, new Comparator<MarketResponse>() {
            @Override
            public int compare(MarketResponse o1, MarketResponse o2) {
                if (o1.getMaxDiscountRate() == o2.getMaxDiscountRate()) {

                    return Double.compare(o2.getAverageReviewScore(), o1.getAverageReviewScore());
                }

                return Double.compare(o2.getMaxDiscountRate(), o1.getMaxDiscountRate());
            }
        });

        return marketResponse;
    }

    private static List<MarketResponse> sortByRecentlyRelease(List<MarketResponse> marketResponse) {
        Collections.sort(marketResponse, new Comparator<MarketResponse>() {
            @Override
            public int compare(MarketResponse o1, MarketResponse o2) {

                return Double.compare(o2.getMarketId(), o1.getMarketId());
            }
        });

        return marketResponse;
    }

    private static List<MarketResponse> sortByDistance(List<MarketResponse> marketResponse, DistanceRequest request) {
        List<DistanceStorage> storages = calculateDistancePerStore(marketResponse, request);

        Collections.sort(storages, new Comparator<DistanceStorage>() {
            @Override
            public int compare(DistanceStorage o1, DistanceStorage o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        return storages.stream()
                .map(DistanceStorage::getMarketResponses)
                .collect(Collectors.toList());
    }

    private static List<DistanceStorage> calculateDistancePerStore(List<MarketResponse> marketResponse, DistanceRequest request) {
        List<DistanceStorage> storages = new ArrayList<>();

        for (MarketResponse store : marketResponse) {
            double distance = calculateDistance(store.getLocationX(), request.getLocationX(), store.getLocationY(), request.getLocationY());

            storages.add(new DistanceStorage(store, distance));
        }

        return storages;
    }

    private static double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


}

