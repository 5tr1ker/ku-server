package com.team.saver.attraction.controller;

import com.team.saver.attraction.dto.AttractionCreateRequest;
import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.attraction.dto.SortType;
import com.team.saver.attraction.service.AttractionService;
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
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping("/v1/attractions")
    @Operation(summary = "관광명소 데이터 추가 ( 16 ) ")
    public ResponseEntity addAttraction(@RequestPart AttractionCreateRequest request,
                                        @RequestPart MultipartFile image) {
        attractionService.addAttraction(request, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/attractions")
    @Operation(summary = "모든 관광명소 데이터 가져오기 ( 17 ) ")
    public ResponseEntity getAttraction(Pageable pageable,
                                        @RequestParam SortType sort,
                                        @RequestParam double locationX,
                                        @RequestParam double locationY) {
        List<AttractionResponse> result = attractionService.getAttraction(pageable, sort, locationX ,locationY);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/attractions/search")
    @Operation(summary = "이름으로 관광명소 데이터 탐색 ( 18 ) ")
    public ResponseEntity searchAttraction(Pageable pageable,
                                           @RequestParam SortType sort,
                                           @RequestParam String keyWord,
                                           @RequestParam double locationX,
                                           @RequestParam double locationY) {
        List<AttractionResponse> result = attractionService.searchAttraction(pageable, sort, keyWord, locationX, locationY);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/attractions/{attractionId}")
    @Operation(summary = "관광명소 데이터 제거 ( 19 ) ")
    public ResponseEntity deleteAttraction(@PathVariable long attractionId) {
        attractionService.deleteAttraction(attractionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
