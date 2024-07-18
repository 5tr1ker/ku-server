package com.team.saver.market.basket.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
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

    @PostMapping("/v1/markets/baskets")
    @Operation(summary = "[ 로그인 ] 장바구니 추가 API ( menuOptionId 가 없으면 0 )")
    public ResponseEntity addBasket(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                    @RequestBody BasketCreateRequest request) {
        basketService.addBasket(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/v1/markets/baskets/{basketMenuId}")
    @Operation(summary = "[ 로그인 ] 장바구니 옵션 수정 API")
    public ResponseEntity updateMenuOption(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                           @RequestBody MenuOptionUpdateRequest request,
                                           @PathVariable long basketMenuId) {
        basketService.updateMenuOption(currentUser, request, basketMenuId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/v1/markets/baskets")
    @Operation(summary = "[ 로그인 ] 특정 아이디로 내가 등록한 장바구니 가져오기")
    public ResponseEntity findByIdAndAccountEmail(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                                  @RequestParam List<Long> ids) {
        List<BasketResponse> result = basketService.findByIdAndAccountEmail(currentUser, ids);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/baskets/all")
    @Operation(summary = "[ 로그인 ] 내가 등록한 장바구니 모두 가져오기")
    public ResponseEntity findAllByAccountEmail(@LogIn @Parameter(hidden = true) CurrentUser currentUser) {
        List<BasketResponse> result = basketService.findAllByAccountEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/markets/baskets/{basketMenuId}")
    @Operation(summary = "[ 로그인 ] 특정 장바구니 삭제")
    public ResponseEntity deleteBasketMenu(@LogIn @Parameter(hidden = true) CurrentUser currentUser,
                                           @PathVariable long basketMenuId) {
        basketService.deleteBasketMenu(currentUser, basketMenuId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
