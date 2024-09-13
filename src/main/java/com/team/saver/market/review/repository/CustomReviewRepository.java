package com.team.saver.market.review.repository;

import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomReviewRepository {

    List<ReviewResponse> findByMarketId(String email, long marketId, SortType sortType, Pageable pageable);

    Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId);

    List<ReviewResponse> findByUserEmail(String email, Pageable pageable);

    Long findRecommenderCountByEmailAndReviewId(String email, long reviewId);

    List<ReviewRecommender> findRecommenderByEmailAndReviewId(String email, long reviewId);

    List<ReviewResponse> findBestReview(String email, long marketId, Pageable pageable);

    List<ReviewResponse> findRandomBestReview(String email, long minimum, Pageable pageable);

    long countReviewByEmail(String email);

    ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId);

    List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId);

    List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId, long size);

    Optional<ReviewResponse> findDetailByReviewId(String email, long reviewId);

    Optional<ReviewStatisticResponse> findReviewStatisticsByEmail(String email);

    long findPhotoReviewCountByMarketId(long marketId);
}
