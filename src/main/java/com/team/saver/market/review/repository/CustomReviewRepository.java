package com.team.saver.market.review.repository;

import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface CustomReviewRepository {

    List<ReviewResponse> findReviewByMarketId(long marketId);

    Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId);

}
