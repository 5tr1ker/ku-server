package com.team.saver.market.store.util;

import com.team.saver.market.store.dto.MarketRecommend;
import com.team.saver.market.store.dto.MarketRecommendRequest;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendAlgorithm {

    private final MarketRepository marketRepository;
    private final MarketRecommend marketRecommend;

    public List<MarketResponse> recommendMarket(MarketRecommendRequest request) {
        return marketRecommend.getMarket(request.getMarketCount());
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")
    protected void updateMarketRecommend() {
        List<MarketResponse> marketList = marketRepository.findMarkets();

        marketRecommend.clearMarket();
        for(MarketResponse marketResponse : marketList) {
            double score = calculateRatingScore(marketResponse.getAverageReviewScore(), marketResponse.getReviewCount());

            marketRecommend.addMarket(marketResponse, score);
        }
    }

    private double calculateRatingScore(double reviewScore, long totalReview) {
        return reviewScore - (reviewScore - 0.5) * Math.pow(2 , Math.log(totalReview + 1));
    }

}
