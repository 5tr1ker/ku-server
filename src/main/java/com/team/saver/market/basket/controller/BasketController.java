package com.team.saver.market.basket.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.market.basket.dto.BasketCreateRequest;
import com.team.saver.market.basket.dto.BasketResponse;
import com.team.saver.market.basket.dto.MenuOptionUpdateRequest;
import com.team.saver.market.basket.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/v1/markets/{marketId}/baskets")
    @Operation(summary = "[ 로그인 ] 장바구니 추가 API ( menuOptionId 가 없으면 0 ) ( 29 )")
    public ResponseEntity addBasket(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                    @PathVariable long marketId,
                                    @RequestBody BasketCreateRequest request) {
        basketService.addBasket(currentUser, marketId, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/v1/markets/baskets/{basketMenuId}")
    @Operation(summary = "[ 로그인 ] 장바구니 옵션 수정 API ( 30 )")
    public ResponseEntity updateMenuOption(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                           @RequestBody MenuOptionUpdateRequest request,
                                           @PathVariable long basketMenuId) {
        basketService.updateMenuOption(currentUser, request, basketMenuId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/v1/markets/baskets")
    @Operation(summary = "[ 로그인 ] 특정 아이디로 내가 등록한 장바구니 가져오기 ( 31 )")
    public ResponseEntity findByIdAndAccountEmail(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                                  @RequestParam List<Long> id) {
        List<BasketResponse> result = basketService.findByIdAndAccountEmail(currentUser, id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/baskets/all")
    @Operation(summary = "[ 로그인 ] 내가 등록한 장바구니 모두 가져오기 ( 32 )")
    public ResponseEntity findAllByAccountEmail(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                                NoOffset noOffset) {
        List<BasketResponse> result = basketService.findAllByAccountEmail(currentUser, noOffset);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/markets/baskets/menus")
    @Operation(summary = "[ 로그인 ] 다중 장바구니 내 메뉴 삭제 ( 33 )")
    public ResponseEntity deleteBasketMenu(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                           @RequestParam List<Long> basketMenuId) {
        basketService.deleteByBasketMenuIds(currentUser, basketMenuId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/v1/markets/baskets")
    @Operation(summary = "[ 로그인 ] 모든 장바구니 삭제 ( 123 )")
    public ResponseEntity deleteAllBasket(@LogIn @Parameter(hidden = true) CurrentUser currentUser) {
        basketService.deleteAllBasket(currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
