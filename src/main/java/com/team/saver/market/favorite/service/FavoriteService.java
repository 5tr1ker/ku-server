package com.team.saver.favorite.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.favorite.entity.Favorite;
import com.team.saver.favorite.repository.FavoriteRepository;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final MarketRepository marketRepository;
    private final AccountRepository accountRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void addFavorite(CurrentUser currentUser, long marketId) {
        if(favoriteRepository.findByUserEmailAndMarketId(currentUser.getEmail(), marketId).isPresent()) {
            throw new CustomRuntimeException(ALREADY_FAVORITE_MARKET);
        };

        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        Favorite favorite = Favorite.createEntity(market, account);
        favoriteRepository.save(favorite);
    }

    public List<MarketResponse> findFavoriteMarketByUserEmail(CurrentUser currentUser) {
        return favoriteRepository.findFavoriteMarketByUserEmail(currentUser.getEmail());
    }

    @Transactional
    public void deleteFavoriteMarketById(CurrentUser currentUser, long marketId) {
        Favorite favorite = favoriteRepository.findByUserEmailAndMarketId(currentUser.getEmail(), marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_FAVORITE));

        favoriteRepository.delete(favorite);
    }

}
