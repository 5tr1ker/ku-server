package com.team.saver.market.store.util;

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
    private final MarketRecommendContainer marketRecommendContainer;

    public List<MarketResponse> recommendMarket(long marketCount) {
        return marketRecommendContainer.getMarket(marketCount);
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void updateMarketRecommend() {
        List<MarketResponse> marketList = marketRepository.findMarkets();

        marketRecommendContainer.clearMarket();
        for(MarketResponse marketResponse : marketList) {
            double score = calculateRatingScore(marketResponse.getAverageReviewScore(), marketResponse.getReviewCount());

            marketRecommendContainer.addMarket(marketResponse, score);
        }
    }

    private double calculateRatingScore(double reviewScore, long totalReview) {
        return reviewScore - (reviewScore - 0.05) * Math.pow(2 , Math.log(totalReview + 1));
    }

}
