package com.team.saver.market.order.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.order.dto.OrderCreateRequest;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/v1/markets/orders")
    @Operation(summary = "[ 로그인 ] 해당 메뉴 주문하기 ( 47 )")
    public ResponseEntity addOrder(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                   @RequestBody OrderCreateRequest request) {
        orderService.addOrder(currentUser , request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/markets/orders/{orderId}")
    @Operation(summary = "[ 로그인 ] 주문 정보 제거 ( 48 )")
    public ResponseEntity deleteOrder(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                      @PathVariable long orderId) {
        orderService.deleteOrder(currentUser, orderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/v1/markets/orders/me")
    @Operation(summary = "[ 로그인 ] 나의 주문 목록 모두 가져오기 ( 49 )")
    public ResponseEntity findOrderByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @RequestParam boolean existReview,
                                               Pageable pageable) {
        List<OrderResponse> result = orderService.findOrderByUserEmail(currentUser, existReview, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/v1/markets/orders/{orderId}")
    @Operation(summary = "[ 로그인 ] 나의 주문 상세 정보 가져오기 ( 50 )")
    public ResponseEntity getOrderDetailByOrderIdAndEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                          @PathVariable long orderId) {
        OrderDetailResponse result = orderService.getOrderDetailByOrderIdAndEmail(currentUser, orderId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
