package com.team.saver.market.review.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.dto.LogInNotEssential;
import com.team.saver.common.exception.CustomRuntimeException;
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

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_REVIEW;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/v1/markets/{marketId}/reviews")
    @Operation(summary = "[ 비 - 로그인 ]마켓에 등록된 리뷰 가져오기 ( 51 )")
    public ResponseEntity findReviewByMarketId(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                               @PathVariable long marketId,
                                               @RequestParam SortType sort,
                                               Pageable pageable) {
        List<ReviewResponse> result = reviewService.findByMarketId(currentUser, marketId, sort, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketId}/statistics")
    @Operation(summary = "리뷰 전체 갯수, 총 평점 및 점수 별 갯수 통계 가져오기 ( 52 )")
    public ResponseEntity findReviewStatisticsByMarketId(@PathVariable long marketId) {
        ReviewStatisticsResponse result = reviewService.findReviewStatisticsByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/reviews/{reviewId}")
    @Operation(summary = "[ 비 - 로그인 ] 특정 ID의 리뷰 데이터 가져오기 ( 53 )")
    public ResponseEntity findDetailByReviewId(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                               @PathVariable long reviewId) {
        ReviewResponse result = reviewService.findDetailByReviewId(currentUser, reviewId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/reviews/statistics")
    @Operation(summary = "[ 로그인 ] 작성한 리뷰, 작성할 수 있는 통계 가져오기 ( 54 )")
    public ResponseEntity findReviewStatisticsByEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        ReviewStatisticResponse result = reviewService.findReviewStatisticsByEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/reviews/top")
    @Operation(summary = "[ 비 - 로그인 ] BEST 리뷰 가져오기 ( 55 )")
    public ResponseEntity findBestReview(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                         @RequestParam(required = false) Long marketId,
                                         Pageable pageable) {
        if (marketId == null) {
            marketId = 0L;
        }
        List<ReviewResponse> result = reviewService.findBestReview(currentUser, marketId, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/reviews/top/random")
    @Operation(summary = "[ 비 - 로그인 ] Best 리뷰 랜덤으로 가져오기 ( 121 )")
    public ResponseEntity findRandomBestReview(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                               @RequestParam long minimum,
                                               Pageable pageable) {
        List<ReviewResponse> result = reviewService.findRandomBestReview(currentUser, minimum, pageable);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/markets/reviews/{reviewId}/recommendation")
    @Operation(summary = "[ 로그인 ] 리뷰 추천하기 ( 56 )")
    public ResponseEntity recommendReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                          @PathVariable long reviewId) {
        reviewService.recommendReview(currentUser, reviewId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/markets/reviews/me")
    @Operation(summary = "[ 로그인 ] 내가 등록한 리뷰 가져오기 ( 57 )")
    public ResponseEntity findByUserEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<ReviewResponse> result = reviewService.findByUserEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/markets/{marketId}/reviews")
    @Operation(summary = "[ 로그인 ] 해당 마켓에 리뷰 추가 ( 58 )")
    public ResponseEntity addReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                    @PathVariable long marketId,
                                    @RequestPart ReviewCreateRequest request,
                                    @RequestPart List<MultipartFile> images) {
        reviewService.addReview(currentUser, marketId, request, images);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/v1/markets/reviews/{reviewId}")
    @Operation(summary = "[ 로그인 ] 리뷰 수정 ( 59 )")
    public ResponseEntity updateReview(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                       @PathVariable long reviewId,
                                       @RequestPart ReviewUpdateRequest request,
                                       @RequestPart(required = false) List<MultipartFile> images) {

        reviewService.updateReview(currentUser, reviewId, request, images);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/markets/{marketId}/reviews/images")
    @Operation(summary = "포토리뷰 가져오기 ( 60 )")
    public ResponseEntity findAllReviewImageByMarketId(@PathVariable long marketId,
                                                       @RequestParam(required = false) Long size) {
        List<PhotoReviewResponse> result = reviewService.findAllReviewImageByMarketId(marketId, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/markets/{marketId}/reviews/images/counts")
    @Operation(summary = "포토리뷰 갯수 가져오기 ( 61 )")
    public ResponseEntity findPhotoReviewCountByMarketId(@PathVariable long marketId) {
        long result = reviewService.findPhotoReviewCountByMarketId(marketId);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/markets/reviews/{reviewId}")
    @Operation(summary = "[ 로그인 ] 리뷰 삭제 ( 62 )")
    public ResponseEntity deleteReview(@PathVariable long reviewId,
                                       @Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        reviewService.deleteReview(currentUser, reviewId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
