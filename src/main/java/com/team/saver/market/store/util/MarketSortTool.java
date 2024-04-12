package com.team.saver.market.store.util;

import com.team.saver.market.store.dto.DistanceRequest;
import com.team.saver.market.store.dto.StoreResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketSortTool {

    public static List<StoreResponse> sortByReviewCount(List<StoreResponse> storeResponses) {
        Collections.sort(storeResponses, new Comparator<StoreResponse>() {
            @Override
            public int compare(StoreResponse o1, StoreResponse o2) {
                return Long.compare(o1.getReviewCount(), o2.getReviewCount());
            }
        });

        return storeResponses;
    }

    public static List<StoreResponse> sortByAverageReviewScore(List<StoreResponse> storeResponses) {
        Collections.sort(storeResponses, new Comparator<StoreResponse>() {
            @Override
            public int compare(StoreResponse o1, StoreResponse o2) {
                return Double.compare(o1.getAverageReviewScore(), o2.getAverageReviewScore());
            }
        });

        return storeResponses;
    }

    public static List<StoreResponse> sortByMaxDiscountRate(List<StoreResponse> storeResponses) {
        Collections.sort(storeResponses, new Comparator<StoreResponse>() {
            @Override
            public int compare(StoreResponse o1, StoreResponse o2) {
                return Double.compare(o1.getMaxDiscountRate(), o2.getMaxDiscountRate());
            }
        });

        return storeResponses;
    }

    public static List<StoreResponse> sortByDistance(List<StoreResponse> storeResponses, DistanceRequest request) {
        List<DistanceStorage> storages = calculateDistancePerStore(storeResponses, request);
        Collections.sort(storages, new Comparator<DistanceStorage>() {
            @Override
            public int compare(DistanceStorage o1, DistanceStorage o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });

        return storages.stream()
                .map(DistanceStorage::getStoreResponses)
                .collect(Collectors.toList());
    }

    private static List<DistanceStorage> calculateDistancePerStore(List<StoreResponse> storeResponses, DistanceRequest request) {
        List<DistanceStorage> storages = new ArrayList<>();

        for(StoreResponse store : storeResponses) {
            double distance = calculateDistance(store.getLocationX(), request.getLocationX(), store.getLocationY(), request.getLocationY());

            storages.add(new DistanceStorage(store, distance));
        }

        return storages;
    }

    private static double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


}
