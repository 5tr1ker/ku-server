package com.team.saver.market.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.entity.Account;
import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.team.saver.market.review.entity.QReviewRecommender.reviewRecommender;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.store.entity.QMarket.market;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReviewResponse> findByMarketId(long marketId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ReviewResponse.class,
                        review.reviewId,
                        account.accountId,
                        account.email,
                        review.content,
                        market.marketId,
                        market.marketName,
                        review.score
                )).from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .innerJoin(review.reviewer, account)
                .fetch();
    }

    @Override
    public Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId) {
        Review result = jpaQueryFactory.select(review)
                .from(review)
                .innerJoin(review.reviewer, account).on(account.email.eq(reviewerEmail))
                .where(review.reviewId.eq(reviewId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ReviewResponse> findByUserEmail(String email) {
        return jpaQueryFactory.select(Projections.constructor(
                        ReviewResponse.class,
                        review.reviewId,
                        account.accountId,
                        account.email,
                        review.content,
                        market.marketId,
                        market.marketName,
                        review.score
                )).from(review)
                .innerJoin(review.market, market)
                .innerJoin(review.reviewer, account).on(account.email.eq(email))
                .fetch();
    }

    @Override
    public Optional<ReviewRecommender> findRecommenderByEmailAndReviewId(String email, long reviewId) {
        ReviewRecommender result = jpaQueryFactory.select(reviewRecommender)
                .from(reviewRecommender)
                .innerJoin(reviewRecommender.account, account).on(account.email.eq(email))
                .innerJoin(reviewRecommender.review, review).on(review.reviewId.eq(reviewId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
