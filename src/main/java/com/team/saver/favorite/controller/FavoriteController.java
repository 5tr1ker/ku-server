package com.team.saver.favorite.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.favorite.service.FavoriteService;
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
@RequestMapping("/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    @Operation(summary = "[ 로그인 ] 해당 가게를 관심 목록 추가")
    public ResponseEntity addFavorite(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                      @RequestParam long marketId) {
        favoriteService.addFavorite(currentUser, marketId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "[ 로그인 ] 내가 추가한 관심 가게 조회")
    public ResponseEntity findFavoriteMarketByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<MarketResponse> result = favoriteService.findFavoriteMarketByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    @Operation(summary = "[ 로그인 ] 내가 추가한 관심 가게 제거")
    public ResponseEntity deleteFavoriteMarketById(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                   @RequestParam long marketId) {
        favoriteService.deleteFavoriteMarketById(currentUser, marketId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
