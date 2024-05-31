package com.team.saver.market.review.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "마켓에 등록된 리뷰 가져오기")
    public ResponseEntity findReviewByMarketId(@RequestParam long marketId, @RequestParam SortType sortType) {
        List<ReviewResponse> result = reviewService.findByMarketId(marketId, sortType);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/statistics")
    @Operation(summary = "리뷰 총 평점 및 점수 별 갯수 통계 가져오기")
    public ResponseEntity findReviewStatisticsByMarketId(@RequestParam long marketId) {
        ReviewStatisticsResponse result = reviewService.findReviewStatisticsByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/top")
    @Operation(summary = "BEST 리뷰 가져오기")
    public ResponseEntity findBestReview(Pageable pageable) {
        List<ReviewResponse> result = reviewService.findBestReview(pageable);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{reviewId}/recommendation")
    @Operation(summary = "리뷰 추천하기")
    public ResponseEntity recommendReview(@LogIn CurrentUser currentUser,
                                          @PathVariable long reviewId) {
        reviewService.recommendReview(currentUser, reviewId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/written-user")
    @Operation(summary = "내가 등록한 리뷰 가져오기")
    public ResponseEntity findByUserEmail(@LogIn CurrentUser currentUser) {
        List<ReviewResponse> result = reviewService.findByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "해당 마켓에 리뷰 추가")
    public ResponseEntity addReview(@LogIn CurrentUser currentUser,
                                    @RequestBody ReviewRequest request) {
        reviewService.addReview(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정")
    public ResponseEntity updateReview(@LogIn CurrentUser currentUser,
                                       @PathVariable long reviewId,
                                       @RequestBody ReviewUpdateRequest request) {

        reviewService.updateReview(currentUser, reviewId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity deleteReview(@PathVariable long reviewId,
                                       @LogIn CurrentUser currentUser) {
        reviewService.deleteReview(currentUser, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
