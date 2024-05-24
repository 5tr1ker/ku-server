package com.team.saver.search.popular.controller;

import com.team.saver.search.popular.dto.PopularSearchRequest;
import com.team.saver.search.popular.dto.SearchWordScoreDto;
import com.team.saver.search.popular.service.PopularSearchService;
import com.team.saver.search.popular.util.SearchWordScheduler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popular-search-word")
public class PopularSearchController {

    private final PopularSearchService popularSearchService;
    private final SearchWordScheduler searchWordScheduler;

    @GetMapping
    public ResponseEntity getPopularSearchWord() {
        List<SearchWordScoreDto> result = popularSearchService.getPopularSearchWord();

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity addSearchWordToRedis(HttpServletRequest httpServletRequest,
                                     @RequestBody PopularSearchRequest request) {

        popularSearchService.addSearchWordToRedis(httpServletRequest, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/1")
    public void a() {
        searchWordScheduler.updateSearchWord_everyTime();
    }

    @PostMapping("/2")
    public void b() {
        searchWordScheduler.resetSearchWord_everyTime();
    }

    @PostMapping("/3")
    public void c() {
        searchWordScheduler.resetSearchWord_everyDay();
    }

    @PostMapping("/4")
    public void d() {
        searchWordScheduler.resetSearchWord_everyWeek();
    }

}
