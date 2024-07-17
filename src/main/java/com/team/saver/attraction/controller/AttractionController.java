package com.team.saver.attraction.controller;

import com.team.saver.attraction.dto.AttractionCreateRequest;
import com.team.saver.attraction.dto.AttractionResponse;
import com.team.saver.attraction.service.AttractionService;
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
    public ResponseEntity addAttraction(@RequestPart AttractionCreateRequest request,
                              @RequestPart MultipartFile image) {
        attractionService.addAttraction(request, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/attractions")
    public ResponseEntity getAttraction(Pageable pageable) {
        List<AttractionResponse> result = attractionService.getAttraction(pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/attractions/search")
    public ResponseEntity searchAttraction(Pageable pageable, String keyWord) {
        List<AttractionResponse> result = attractionService.searchAttraction(pageable, keyWord);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/attractions/{attractionId}")
    public ResponseEntity deleteAttraction(@PathVariable long attractionId) {
        attractionService.deleteAttraction(attractionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
