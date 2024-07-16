package com.team.saver.promotion.controller;

import com.team.saver.promotion.dto.PromotionResponse;
import com.team.saver.promotion.dto.PromotionCreateRequest;
import com.team.saver.promotion.entity.PromotionLocation;
import com.team.saver.promotion.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/v1/{location}/promotions")
    @Operation(summary = "특정 위치에 있는 홍보 데이터 등록")
    public ResponseEntity addPromotionFromMain(@RequestPart PromotionCreateRequest request,
                                               @PathVariable PromotionLocation location,
                                               @RequestPart MultipartFile image) {
        promotionService.addPromotion(request, location, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/{location}/promotions/{promotionId}")
    @Operation(summary = "특정 위치에 있는 홍보 데이터 제거")
    public ResponseEntity deletePromotion(@PathVariable long promotionId,
                                          @PathVariable PromotionLocation location) {
        promotionService.deleteByIdAndLocation(promotionId, location);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/v1/{location}/promotions")
    @Operation(summary = "특정 위치의 홍보 데이터 조회 [ MAIN : 메인 , ATTRACTION : 관광 조회 페이지 ]")
    public ResponseEntity getPromotion(Pageable pageable, @PathVariable PromotionLocation location) {
        List<PromotionResponse> result = promotionService.getPromotion(pageable, location);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
