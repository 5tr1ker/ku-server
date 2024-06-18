package com.team.saver.search.autocomplete.controller;

import com.team.saver.search.autocomplete.dto.AutoCompleteRequest;
import com.team.saver.search.autocomplete.dto.UtilInitDto;
import com.team.saver.search.autocomplete.service.AutoCompleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutoCompleteController {

    private final AutoCompleteService autoCompleteService;

    @PostMapping("/v1/auto-complete")
    @Operation(summary = "자동 완성 글자 추가")
    public ResponseEntity addSearchWord(@RequestBody AutoCompleteRequest request) {
        autoCompleteService.addSearchWord(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/auto-complete")
    @Operation(summary = "빈도수를 기반으로 자동 검색 탐색")
    public ResponseEntity findSearchComplete(@RequestParam String word) {
        List<UtilInitDto> result = autoCompleteService.findSearchComplete(word);

        return ResponseEntity.ok(result);
    }

}