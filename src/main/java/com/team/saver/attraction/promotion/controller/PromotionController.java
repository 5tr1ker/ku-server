package com.team.saver.attraction.promotion.controller;

import com.team.saver.attraction.promotion.dto.PromotionResponse;
import com.team.saver.attraction.promotion.dto.PromotionCreateRequest;
import com.team.saver.attraction.promotion.service.PromotionService;
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

    @PostMapping("/v1/attractions/promotions")
    @Operation(summary = "관광 시설 홍보 등록")
    public ResponseEntity addPromotion(@RequestPart PromotionCreateRequest request,
                                        @RequestPart MultipartFile image) {
        promotionService.addAttraction(request, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/attractions/promotions/{promotionId}")
    @Operation(summary = "관광 시설 홍보 제거")
    public ResponseEntity deletePromotion(@PathVariable long promotionId) {
        promotionService.deleteAttraction(promotionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/v1/attractions/promotions")
    @Operation(summary = "모든 관광 시설 홍보 조회")
    public ResponseEntity getPromotion(Pageable pageable) {
        List<PromotionResponse> result = promotionService.getAttraction(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
