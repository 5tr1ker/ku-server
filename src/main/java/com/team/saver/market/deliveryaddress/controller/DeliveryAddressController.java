package com.team.saver.market.deliveryaddress.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressCreateRequest;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressResponse;
import com.team.saver.market.deliveryaddress.dto.DeliveryAddressUpdateRequest;
import com.team.saver.market.deliveryaddress.service.DeliveryAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @PostMapping("/v1/markets/delivery-addresses")
    @Operation(summary = "[ 로그인 ] 주소지 데이터 추가 ( 37 )")
    public ResponseEntity addDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                             @RequestBody DeliveryAddressCreateRequest request) {
        deliveryAddressService.addDeliveryAddress(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/v1/markets/delivery-addresses/{deliveryAddressId}")
    @Operation(summary = "[ 로그인 ] 주소지 데이터 수정 ( 38 )")
    public ResponseEntity updateDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                @PathVariable long deliveryAddressId,
                                                @RequestBody DeliveryAddressUpdateRequest request) {
        deliveryAddressService.updateDeliveryAddress(currentUser, deliveryAddressId, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/v1/markets/delivery-addresses/{deliveryAddressId}")
    @Operation(summary = "[ 로그인 ] 주소지 데이터 삭제 ( 39 )")
    public ResponseEntity deleteDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                @PathVariable long deliveryAddressId) {
        deliveryAddressService.deleteDeliveryAddress(currentUser, deliveryAddressId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/v1/markets/delivery-addresses/{deliveryAddressId}/default-addresses")
    @Operation(summary = "[ 로그인 ] 기본 배송지 설정 ( 40 )")
    public ResponseEntity updateDefaultDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                       @PathVariable long deliveryAddressId) {
        deliveryAddressService.updateDefaultDeliveryAddress(currentUser, deliveryAddressId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/v1/markets/delivery-addresses")
    @Operation(summary = "[ 로그인 ] 나의 배송지 데이터 전부 가져오기 ( 41 )")
    public ResponseEntity findDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<DeliveryAddressResponse> result = deliveryAddressService.findDeliveryAddress(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/delivery-addresses/{deliveryAddressId}")
    @Operation(summary = "[ 로그인 ] 배송지 상세 데이터 하나 가져오기 ( 42 )")
    public ResponseEntity findDeliveryAddress(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                              @PathVariable long deliveryAddressId) {
        DeliveryAddressResponse result = deliveryAddressService.findDeliveryAddressById(currentUser, deliveryAddressId);

        return ResponseEntity.ok(result);
    }

}
