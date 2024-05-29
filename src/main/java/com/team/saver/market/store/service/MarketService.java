package com.team.saver.market.store.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.Classification;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.MarketClassification;
import com.team.saver.market.store.entity.QMarket;
import com.team.saver.market.store.repository.ClassificationRepository;
import com.team.saver.market.store.repository.MarketClassificationRepository;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.EXIST_CLASSIFICATION;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_MARKET;
import static com.team.saver.market.store.entity.QMarket.market;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketSortTool marketSortTool;
    private final ClassificationRepository classificationRepository;
    private final MarketClassificationRepository marketClassificationRepository;
    private final AccountService accountService;

    public List<MarketResponse> findAllMarket(SearchMarketRequest request) {
        return marketSortTool.sortMarket(request.getSort(), request.getDistance(), null);
    }

    public List<MarketResponse> findMarketByMarketName(SearchByNameRequest request) {
        QMarket market = new QMarket("market");

        return marketSortTool.sortMarket(request.getSort(), request.getDistance(), market.marketName.contains(request.getMarketName()));
    }

    public List<MarketResponse> findMarketByMainCategory(SearchByCategoryRequest request) {
        return marketSortTool.sortMarket(request.getSort(), request.getDistance(), market.mainCategory.eq(request.getCategory()));
    }

    public List<MarketResponse> findMarketByMainCategoryAndMarketName(SearchByCategoryAndNameRequest request) {
        return marketSortTool.sortMarket(request.getSort(), request.getDistance(), market.mainCategory.eq(request.getCategory()).and(market.marketName.contains(request.getMarketName())));
    }

    public MarketDetailResponse findMarketDetailById(long marketId) {
        Market market = marketRepository.findMarketDetailById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        return MarketDetailResponse.createResponse(market);
    }

    @Transactional
    public void addMarket(CurrentUser currentUser, MarketRequest request) {
        Account account = accountService.getProfile(currentUser);

        Market market = Market.createEntity(account, request, "image");
        for (String classification : new HashSet<>(Arrays.asList(request.getClassifications()))) {
            Classification classificationEntity = getClassificationEntity(classification);

            MarketClassification marketClassification = MarketClassification.createEntity(classificationEntity, market);

            market.addClassification(marketClassification);
        }

        marketRepository.save(market);
    }

    private Classification getClassificationEntity(String classification) {
        return classificationRepository.findByClassification(classification)
                .orElseGet(() -> classificationRepository.save(Classification.createEntity(classification)));
    }

    @Transactional
    protected void addClassification(Market market, String classification) {
        Classification classificationEntity = getClassificationEntity(classification);

        if (marketClassificationRepository.findByMarketAndClassification(market, classificationEntity).isPresent()) {
            throw new CustomRuntimeException(EXIST_CLASSIFICATION);
        }
        ;

        MarketClassification marketClassification = MarketClassification.createEntity(classificationEntity, market);

        market.addClassification(marketClassification);
    }

}
