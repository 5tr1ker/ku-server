package com.team.saver.market.store.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.*;
import com.team.saver.market.store.repository.ClassificationRepository;
import com.team.saver.market.store.repository.MarketClassificationRepository;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import com.team.saver.s3.service.S3Service;
import com.team.saver.search.autocomplete.service.AutoCompleteService;
import com.team.saver.search.autocomplete.util.Trie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;
import static com.team.saver.market.store.entity.QMarket.market;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketSortTool marketSortTool;
    private final ClassificationRepository classificationRepository;
    private final MarketClassificationRepository marketClassificationRepository;
    private final AccountService accountService;
    private final AutoCompleteService autoCompleteService;
    private final S3Service s3Service;

    public List<MarketResponse> findAllMarket(MarketSearchRequest request, Pageable pageable) {
        return marketSortTool.sortMarket(request, null, pageable);
    }

    public List<MarketResponse> findMarketByMarketName(MarketSearchRequest request, String marketName, Pageable pageable) {
        QMarket market = new QMarket("market");

        return marketSortTool.sortMarket(request, market.marketName.contains(marketName), pageable);
    }

    public List<MarketResponse> findMarketByMainCategory(MarketSearchRequest request, MainCategory category, Pageable pageable) {
        return marketSortTool.sortMarket(request, market.mainCategory.eq(category), pageable);
    }

    public List<MarketResponse> findMarketByMainCategoryAndMarketName(MarketSearchRequest request, MainCategory categoryData, String marketName, Pageable pageable) {
        return marketSortTool.sortMarket(request, market.mainCategory.eq(categoryData).and(market.marketName.contains(marketName)), pageable);
    }

    public MarketDetailResponse findMarketDetailById(long marketId) {
        return marketRepository.findMarketDetailById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
    }

    @Transactional
    public void addMarket(CurrentUser currentUser, MarketCreateRequest request) {
        Account account = accountService.getProfile(currentUser);

        Market market = Market.createEntity(account, request, "image");
        for (String classification : request.getClassifications()) {
            Classification classificationEntity = getClassificationEntity(classification);

            MarketClassification marketClassification = MarketClassification.createEntity(classificationEntity, market);

            market.addClassification(marketClassification);
        }

        marketRepository.save(market);
        autoCompleteService.addSearchWord(market.getMarketName());
    }

    @Transactional
    public void addMarketMenu(CurrentUser currentUser, long marketId, List<MenuCreateRequest> request, List<MultipartFile> image) {
        Market market = marketRepository.findMarketByMarketIdAndPartnerEmail(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        int index = 0;
        for(MenuCreateRequest menuCreateRequest : request) {
            String imageUrl = s3Service.uploadImage(image.get(index++));
            Menu menu = Menu.createEntity(menuCreateRequest, imageUrl);

            for(MenuOptionCreateRequest menuOptionCreateRequest : menuCreateRequest.getOptions()) {
                MenuOption menuOption = MenuOption.createEntity(menuOptionCreateRequest);

                menu.addMenuOption(menuOption);
            }

            market.addMenu(menu);
        }
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

    public List<MenuResponse> findMarketMenuById(long marketId) {
        return marketRepository.findMarketMenuById(marketId);
    }

    @Transactional
    public void modifyEventMessage(MarketEventUpdateRequest request, CurrentUser currentUser, long marketId) {
        Market market = marketRepository.findMarketByMarketIdAndPartnerEmail(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_ACCESS_OWNER_PARTNER));

        market.setEventMessage(request.getMessage());
    }

}
