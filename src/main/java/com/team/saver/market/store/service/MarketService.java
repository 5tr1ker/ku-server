package com.team.saver.market.store.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.store.dto.*;
import com.team.saver.market.store.entity.*;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import com.team.saver.market.store.util.SortType;
import com.team.saver.s3.service.S3Service;
import com.team.saver.search.autocomplete.service.AutoCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;
import static com.team.saver.market.store.entity.QMarket.market;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketSortTool marketSortTool;
    private final AccountService accountService;
    private final AutoCompleteService autoCompleteService;
    private final S3Service s3Service;

    public List<MarketResponse> findAllMarket(MarketSearchRequest request, Pageable pageable) {
        if(request.getSort().equals(SortType.RECENTLY_UPLOAD)) {

            return marketSortTool.sortMarket(request, market.createDate.after(LocalDate.now().minusDays(30)), pageable);
        }

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

    public MarketDetailResponse findMarketDetailById(CurrentUser currentUser, long marketId) {
        return marketRepository.findMarketDetailById(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
    }

    @Transactional
    public void addMarket(CurrentUser currentUser, MarketCreateRequest request, MultipartFile image) {
        Account account = accountService.getProfile(currentUser);
        String imageUrl = s3Service.uploadImage(image);

        Market market = Market.createEntity(account, request, imageUrl);

        marketRepository.save(market);
        autoCompleteService.addSearchWord(market.getMarketName());
    }

    @Transactional
    public void addMarketMenu(CurrentUser currentUser, long marketId, List<MenuCreateRequest> request, List<MultipartFile> image) {
        Market market = marketRepository.findMarketByMarketIdAndPartnerEmail(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        int index = 0;
        long priority_menuContainer = 1;
        long priority_menuOptionContainer = 1;
        for(MenuCreateRequest menuCreateRequest : request) {

            MenuContainer menuContainer = MenuContainer.createEntity(menuCreateRequest.getClassification(), priority_menuContainer++);

            for(MenuCreateData menuCreateData : menuCreateRequest.getMenus()) {
                String imageUrl = s3Service.uploadImage(image.get(index++));

                Menu menu = Menu.createEntity(menuCreateData, imageUrl);

                for(MenuOptionContainerCreateRequest menuOptionContainerCreateRequest : menuCreateData.getOptionContainers()) {
                    MenuOptionContainer menuOptionContainer = MenuOptionContainer.createEntity(menuOptionContainerCreateRequest, priority_menuOptionContainer++);

                    for(MenuOptionCreateRequest menuOptionCreateRequest : menuOptionContainerCreateRequest.getMenuOption()) {
                        MenuOption menuOption = MenuOption.createEntity(menuOptionCreateRequest);

                        menuOptionContainer.addMenuOption(menuOption);
                    }

                    menu.addMenuOptionContainer(menuOptionContainer);

                }

                menuContainer.addMenu(menu);
            }

            market.addMenuContainer(menuContainer);
        }
    }

    public List<MenuClassificationResponse> findMarketMenuById(long marketId) {
        return marketRepository.findMarketMenuById(marketId);
    }

    @Transactional
    public void modifyEventMessage(MarketEventUpdateRequest request, CurrentUser currentUser, long marketId) {
        Market market = marketRepository.findMarketByMarketIdAndPartnerEmail(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_ACCESS_OWNER_PARTNER));

        market.setEventMessage(request.getMessage());
    }

    public List<MenuOptionClassificationResponse> findMarketMenuAndOptionById(long menuId) {
        return marketRepository.findMenuOptionById(menuId);
    }

    public MarketResponse findByMarketName(String marketName) {
        return marketRepository.findByMarketName(marketName)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));
    }

    public List<MarketCreateDateResponse> findMarketCreateDate(Pageable pageable) {
        return marketRepository.findMarketCreateDate(pageable);
    }

    public long findMarketCount() {
        return marketRepository.findMarketCount();
    }

    public List<MarketCreateDateResponse> findMarketCreateDateByMarketName(String marketName, Pageable pageable) {
        return marketRepository.findMarketCreateDateByMarketName(marketName, pageable);
    }
}
