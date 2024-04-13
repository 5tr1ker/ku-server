package com.team.saver.market.review.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.market.review.dto.ReviewRequest;
import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{marketId}")
    @Operation(summary = "리뷰 가져오기")
    public ResponseEntity findReviewByMarketId(@PathVariable long marketId) {
        List<ReviewResponse> result = reviewService.findReviewByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{marketId}")
    @Operation(summary = "리뷰 추가")
    public ResponseEntity addReview(@PathVariable long marketId,
                                    @LogIn CurrentUser currentUser,
                                    @RequestBody ReviewRequest request) {
        reviewService.addReview(marketId, currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity deleteReview(@PathVariable long reviewId,
                                       @LogIn CurrentUser currentUser) {
        reviewService.deleteReview(currentUser, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
