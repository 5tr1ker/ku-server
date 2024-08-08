package com.team.saver.search.autocomplete.controller;

import com.team.saver.search.autocomplete.dto.WordAddRequest;
import com.team.saver.search.autocomplete.dto.WordResponse;
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
    @Operation(summary = "자동 완성 글자 추가 ( 95 )")
    public ResponseEntity addSearchWord(@RequestBody WordAddRequest request) {
        autoCompleteService.addSearchWord(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/auto-complete")
    @Operation(summary = "빈도수를 기반으로 자동 검색 탐색 ( 96 )")
    public ResponseEntity findSearchComplete(@RequestParam String word) {
        List<WordResponse> result = autoCompleteService.findSearchComplete(word);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/auto-complete")
    @Operation(summary = "자동 완성 글자 제거 [ 삭제하면 검색 수와 함께 삭제됩니다. ] ( 97 )")
    public ResponseEntity deleteSearchWord(@RequestParam String word) {
        autoCompleteService.deleteWord(word);

        return ResponseEntity.noContent().build();
    }

}