package com.team.saver.market.favorite.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.common.util.DistanceCalculator;
import com.team.saver.market.favorite.entity.Favorite;
import com.team.saver.market.favorite.repository.FavoriteRepository;
import com.team.saver.market.store.dto.MarketResponse;
import com.team.saver.market.store.entity.Market;
import com.team.saver.market.store.repository.MarketRepository;
import com.team.saver.market.store.util.MarketSortTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final MarketRepository marketRepository;
    private final AccountRepository accountRepository;
    private final FavoriteRepository favoriteRepository;

    @Transactional(noRollbackFor = CustomRuntimeException.class)
    public void addFavorite(CurrentUser currentUser, long marketId) {
        Optional<Favorite> favoriteOptional = favoriteRepository.findByUserEmailAndMarketId(currentUser.getEmail(), marketId);

        if(favoriteOptional.isPresent()) {
            favoriteRepository.delete(favoriteOptional.get());

            throw new CustomRuntimeException(DELETE_FAVORITE_MARKET);
        }

        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        Market market = marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_MARKET));

        Favorite favorite = Favorite.createEntity(market, account);
        favoriteRepository.save(favorite);
    }

    public List<MarketResponse> findFavoriteMarketByUserEmail(CurrentUser currentUser, double locationX, double locationY, NoOffset noOffset) {
        List<MarketResponse> result = favoriteRepository.findFavoriteMarketByUserEmail(currentUser.getEmail(), noOffset);

        return DistanceCalculator.calculateMarketDistance(result, locationX, locationY);
    }

    @Transactional
    public void deleteFavoriteIds(CurrentUser currentUser, List<Long> ids) {
        List<Favorite> favorite = favoriteRepository.findByUserEmailAndMarketIds(currentUser.getEmail(), ids);

        favoriteRepository.deleteAll(favorite);
    }

    public long findFavoriteMarketCountByUserEmail(CurrentUser currentUser) {
        return favoriteRepository.findFavoriteMarketCountByUserEmail(currentUser.getEmail());
    }
}
