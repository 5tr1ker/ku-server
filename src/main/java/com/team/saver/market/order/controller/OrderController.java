package com.team.saver.market.order.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.order.dto.NewOrderRequest;
import com.team.saver.market.order.dto.OrderDetailResponse;
import com.team.saver.market.order.dto.OrderResponse;
import com.team.saver.market.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "[ 로그인 ] 주문 목록 추가")
    public ResponseEntity addOrder(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                   @RequestBody NewOrderRequest request) {
        orderService.addOrder(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "[ 로그인 ] 주문 목록 제거")
    public ResponseEntity deleteOrder(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                      @PathVariable long orderId) {
        orderService.deleteOrder(currentUser, orderId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(summary = "[ 로그인 ] 주문 목록 모두 가져오기")
    public ResponseEntity findOrderByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<OrderResponse> result = orderService.findOrderByUserEmail(currentUser);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "[ 로그인 ] 주문 상세 정보 가져오기")
    public ResponseEntity getOrderDetailByOrderIdAndEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                          @PathVariable long orderId) {
        OrderDetailResponse result = orderService.getOrderDetailByOrderIdAndEmail(currentUser, orderId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
