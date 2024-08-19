package com.team.saver.market.review.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomReviewRepository {

    List<ReviewResponse> findByMarketId(long marketId, SortType sortType);

    Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId);

    List<ReviewResponse> findByUserEmail(String email);

    Long findRecommenderCountByEmailAndReviewId(String email, long reviewId);

    List<ReviewResponse> findBestReview(Pageable pageable);

    long countReviewByEmail(String email);

    ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId);

    List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId);

    List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId, long size);

    Optional<ReviewResponse> findDetailByReviewId(long reviewId);

    Optional<ReviewStatisticResponse> findReviewStatisticsByEmail(String email);

    long findPhotoReviewCountByMarketId(long marketId);
}
