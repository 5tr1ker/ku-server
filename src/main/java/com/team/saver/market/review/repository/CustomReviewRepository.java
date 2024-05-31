package com.team.saver.market.review.repository;

import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.dto.ReviewStatistics;
import com.team.saver.market.review.dto.ReviewStatisticsResponse;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomReviewRepository {

    List<ReviewResponse> findByMarketId(long marketId);

    Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId);

    List<ReviewResponse> findByUserEmail(String email);

    Optional<ReviewRecommender> findRecommenderByEmailAndReviewId(String email, long reviewId);

    List<ReviewResponse> findBestReview(Pageable pageable);

    ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId);

}
