package com.team.saver.market.review.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/v1/markets/{marketId}/reviews")
    @Operation(summary = "마켓에 등록된 리뷰 가져오기")
    public ResponseEntity findReviewByMarketId(@PathVariable long marketId, @RequestParam SortType sort) {
        List<ReviewResponse> result = reviewService.findByMarketId(marketId, sort);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketId}/statistics")
    @Operation(summary = "리뷰 총 평점 및 점수 별 갯수 통계 가져오기")
    public ResponseEntity findReviewStatisticsByMarketId(@PathVariable long marketId) {
        ReviewStatisticsResponse result = reviewService.findReviewStatisticsByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/reviews/top")
    @Operation(summary = "BEST 리뷰 가져오기")
    public ResponseEntity findBestReview(Pageable pageable) {
        List<ReviewResponse> result = reviewService.findBestReview(pageable);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/markets/reviews/{reviewId}/recommendation")
    @Operation(summary = "[ 로그인 ] 리뷰 추천하기")
    public ResponseEntity recommendReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                          @PathVariable long reviewId) {
        reviewService.recommendReview(currentUser, reviewId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/markets/reviews/me")
    @Operation(summary = "[ 로그인 ] 내가 등록한 리뷰 가져오기")
    public ResponseEntity findByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<ReviewResponse> result = reviewService.findByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/markets/{marketId}/reviews")
    @Operation(summary = "[ 로그인 ] 해당 마켓에 리뷰 추가")
    public ResponseEntity addReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                    @PathVariable long marketId,
                                    @RequestPart ReviewRequest request,
                                    @RequestPart List<MultipartFile> images) {
        reviewService.addReview(currentUser , marketId , request , images);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/v1/markets/reviews/{reviewId}")
    @Operation(summary = "[ 로그인 ] 리뷰 수정")
    public ResponseEntity updateReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                       @PathVariable long reviewId,
                                       @RequestPart ReviewUpdateRequest request,
                                       @RequestPart(required = false) List<MultipartFile> images) {

        reviewService.updateReview(currentUser, reviewId, request, images);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/markets/{marketId}/reviews/images")
    @Operation(summary = "포토리뷰 가져오기")
    public ResponseEntity findAllReviewImageByMarketId(@PathVariable long marketId) {
        List<PhotoReviewResponse> result = reviewService.findAllReviewImageByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/markets/reviews/{reviewId}")
    @Operation(summary = "[ 로그인 ] 리뷰 삭제")
    public ResponseEntity deleteReview(@PathVariable long reviewId,
                                       @Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        reviewService.deleteReview(currentUser, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
