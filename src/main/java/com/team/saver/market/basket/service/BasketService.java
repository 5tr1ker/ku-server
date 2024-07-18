package com.team.saver.market.basket.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.ErrorMessage;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.market.basket.dto.BasketCreateRequest;
import com.team.saver.market.basket.dto.MenuOptionUpdateRequest;
import com.team.saver.market.basket.entity.Basket;
import com.team.saver.market.basket.entity.BasketMenu;
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
    public void addBasket(CurrentUser currentUser, BasketCreateRequest request) {
        Account account = accountService.getProfile(currentUser);
        Basket basket = basketRepository.findByMarketIdAndAccountEmail(request.getMarketId(), currentUser.getEmail())
                .orElseGet(() -> createBasket(request.getMarketId(), account));

        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MENU));

        MenuOption menuOption = null;
        if(request.getMenuOptionId() != 0) {
            menuOption = menuOptionRepository.findById(request.getMenuOptionId())
                    .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MENU_OPTION));
        }

        BasketMenu basketMenu = BasketMenu.createEntity(menu, menuOption, request.getAmount());
        basket.addBasketMenu(basketMenu);
    }

    private Basket createBasket(long marketId, Account account) {
        Market market = marketRepository.findById(marketId)
                        .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        return Basket.createEntity(market, account);
    }

    @Transactional
    public void updateMenuOption(CurrentUser currentUser, MenuOptionUpdateRequest request) {

    }

    public void findBasketByAccountEmail() {

    }

}
