package com.team.saver.market.store.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.coupon.entity.QCoupon;
import com.team.saver.market.review.entity.QReview;
import com.team.saver.market.store.dto.DistanceRequest;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.QMarket;
import com.team.saver.market.store.repository.MarketRepository;
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

    private final MarketRepository marketRepository;

    public List<MarketResponse> sortMarket(SortType sort, DistanceRequest request, BooleanExpression conditional) {
        if (sort.equals(SortType.NEAR_DISTANCE)) {
            List<MarketResponse> marketResponse = marketRepository.findMarketsByConditional(conditional);

            return sortByDistance(marketResponse, request);
        } else if (sort.equals(SortType.HIGHEST_DISCOUNT)) {
            return sortByHighestDiscountRate(conditional);
        } else if (sort.equals(SortType.HIGHEST_RATED)) {
            return sortByHighAverageReviewScore(conditional);
        } else if (sort.equals(SortType.MANY_REVIEW)) {
            return sortByManyReviewCount(conditional);
        } else if (sort.equals(SortType.RECENTLY_UPLOAD)) {
            return sortByRecentlyUpload(conditional);
        }

        throw new CustomRuntimeException(NOT_FOUND_SORT_TYPE);
    }

    private List<MarketResponse> sortByManyReviewCount(BooleanExpression conditional) {
        QReview review = new QReview("review");

        return marketRepository.findMarketsAndSort(review.count().desc(), conditional);
    }

    private List<MarketResponse> sortByHighAverageReviewScore(BooleanExpression conditional) {
        QReview review = new QReview("review");

        return marketRepository.findMarketsAndSort(review.score.avg().desc(), conditional);
    }

    private List<MarketResponse> sortByHighestDiscountRate(BooleanExpression conditional) {
        QCoupon coupon = new QCoupon("coupon");

        return marketRepository.findMarketsAndSort(coupon.saleRate.max().desc(), conditional);
    }

    private List<MarketResponse> sortByRecentlyUpload(BooleanExpression conditional) {
        QMarket market = new QMarket("market");

        return marketRepository.findMarketsAndSort(market.marketId.desc(), conditional);
    }

    private List<MarketResponse> sortByDistance(List<MarketResponse> marketResponse, DistanceRequest request) {
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

    private List<DistanceStorage> calculateDistancePerStore(List<MarketResponse> marketResponse, DistanceRequest request) {
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

