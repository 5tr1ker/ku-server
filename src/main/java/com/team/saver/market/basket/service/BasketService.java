package com.team.saver.market.basket.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.basket.dto.BasketCreateRequest;
import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.dto.MenuOptionUpdateRequest;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.basket.entity.BasketMenu;
import com.team.saver.market.basket.entity.BasketMenuOption;
import com.team.saver.market.basket.repository.BasketMenuRepository;
import com.team.saver.market.basket.repository.BasketRepository;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.entity.Menu;
import com.team.saver.market.store.entity.MenuOption;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.repository.MenuOptionRepository;
import com.team.saver.market.store.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketMenuRepository basketMenuRepository;
    private final AccountService accountService;
    private final MarketRepository marketRepository;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;

    @Transactional
    public void addBasket(CurrentUser currentUser, long marketId, BasketCreateRequest request) {
        Account account = accountService.getProfile(currentUser);
        Basket basket = basketRepository.findByMarketIdAndAccountEmail(marketId, currentUser.getEmail())
                .orElseGet(() -> basketRepository.save(createBasket(marketId, account)));

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MENU));

        BasketMenu basketMenu = BasketMenu.createEntity(menu, request.getAmount());
        List<MenuOption> menuOptions = menuOptionRepository.findAllById(request.getMenuOptionIds());
        basketMenu.updateMenuOption(menuOptions);

        basket.addBasketMenu(basketMenu);
    }

    private Basket createBasket(long marketId, Account account) {
        Market market = marketRepository.findById(marketId)
                        .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        return Basket.createEntity(market, account);
    }

    @Transactional
    public void updateMenuOption(CurrentUser currentUser, MenuOptionUpdateRequest request, long basketMenuId) {
        BasketMenu basketMenu = basketMenuRepository.findByAccountEmailAndId(currentUser.getEmail(), basketMenuId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_BASKET_MENU));

        List<MenuOption> menuOption = menuOptionRepository.findAllById(request.getMenuOptionIds());

        basketMenu.updateMenuOption(menuOption);
        basketMenu.updateAmount(request.getAmount());
    }

    public List<BasketResponse> findByIdAndAccountEmail(CurrentUser currentUser, List<Long> ids) {
        return basketRepository.findByIdAndAccountEmail(currentUser.getEmail(), ids);
    }

    public List<BasketResponse> findAllByAccountEmail(CurrentUser currentUser, NoOffset noOffset) {
        return basketRepository.findAllByAccountEmail(currentUser.getEmail(), noOffset);
    }

    @Transactional
    public void deleteByBasketMenuIds(CurrentUser currentUser, List<Long> basketMenuId) {
        basketMenuRepository.deleteByBasketMenuIds(currentUser.getEmail(), basketMenuId);
    }

}
