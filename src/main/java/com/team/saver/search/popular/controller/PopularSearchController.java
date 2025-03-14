package com.team.saver.search.popular.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.search.popular.dto.PopularSearchAddRequest;
import com.team.saver.search.popular.dto.SearchWordScore;
import com.team.saver.search.popular.service.PopularSearchService;
import com.team.saver.search.popular.util.SearchWordScheduler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PopularSearchController {

    private final PopularSearchService popularSearchService;

    @GetMapping("/v1/popular-search-word")
    @Operation(summary = "인기 검색어 조회 ( 98 )")
    public ResponseEntity getPopularSearchWord(@RequestParam int size) {
        List<SearchWordScore.Node> result = popularSearchService.getPopularSearchWord(size);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/popular-search-word")
    @Operation(summary = "[ 로그인 ] 인기 검색어 업데이트 및 등록 ( 99 )")
    public ResponseEntity addSearchWordToRedis(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @RequestBody PopularSearchAddRequest request) {

        popularSearchService.addSearchWordToRedis(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private final SearchWordScheduler scheduler;
    @PostMapping("/v1/popular-search-word/push")
    @Operation(summary = "[ 테스트 ] 인기 검색어 Redis 에서 서버에 즉시 반영 ( 100 )")
    public ResponseEntity pushSearchWordFromRedis() {
        scheduler.updateSearchWord_everyTime();

        return ResponseEntity.ok().build();
    }

}
