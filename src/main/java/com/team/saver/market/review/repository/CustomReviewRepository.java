package com.team.saver.market.review.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.team.saver.market.review.dto.PhotoReviewResponse;
import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.dto.ReviewStatisticResponse;
import com.team.saver.market.review.dto.ReviewStatisticsResponse;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomReviewRepository {

    List<ReviewResponse> findByMarketId(long marketId, OrderSpecifier ...orderSpecifier);

    Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId);

    List<ReviewResponse> findByUserEmail(String email);

    Optional<ReviewRecommender> findRecommenderByEmailAndReviewId(String email, long reviewId);

    List<ReviewResponse> findBestReview(Pageable pageable);

    long countReviewByEmail(String email);

    ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId);

    List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId);

    Optional<ReviewResponse> findDetailByReviewId(long reviewId);

    Optional<ReviewStatisticResponse> findReviewStatisticsByEmail(String email);

    long findPhotoReviewCountByMarketId(long marketId);
}
