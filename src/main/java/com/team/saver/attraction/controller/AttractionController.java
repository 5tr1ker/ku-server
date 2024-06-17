package com.team.saver.attraction.controller;

import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.attraction.dto.NewAttractionRequest;
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
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    @PostMapping
    @Operation(summary = "관광 시설 등록")
    public ResponseEntity addAttraction(@RequestPart NewAttractionRequest request,
                                        @RequestPart MultipartFile imageFile) {
        attractionService.addAttraction(request, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{attractionId}")
    @Operation(summary = "관광 시설 제거")
    public ResponseEntity deleteAttraction(@PathVariable long attractionId) {
        attractionService.deleteAttraction(attractionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @Operation(summary = "관광 시설 조회")
    public ResponseEntity getAttraction(Pageable pageable) {
        List<AttractionResponse> result = attractionService.getAttraction(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
