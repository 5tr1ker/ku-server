package com.team.saver.market.favorite.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.favorite.service.FavoriteService;
import com.team.saver.market.store.dto.MarketResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/v1/markets/{marketId}/favorites")
    @Operation(summary = "[ 로그인 ] 해당 가게를 관심 목록 추가")
    public ResponseEntity addFavorite(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                      @PathVariable long marketId) {
        favoriteService.addFavorite(currentUser, marketId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/markets/favorites")
    @Operation(summary = "[ 로그인 ] 내가 추가한 관심 가게 조회")
    public ResponseEntity findFavoriteMarketByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                        @RequestParam double locationX,
                                                        @RequestParam double locationY) {
        List<MarketResponse> result = favoriteService.findFavoriteMarketByUserEmail(currentUser, locationX, locationY);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/markets/favorites")
    @Operation(summary = "[ 로그인 ] 내가 추가한 관심 가게 제거")
    public ResponseEntity deleteFavoriteIds(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                   @RequestParam List<Long> id) {
        favoriteService.deleteFavoriteIds(currentUser, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
