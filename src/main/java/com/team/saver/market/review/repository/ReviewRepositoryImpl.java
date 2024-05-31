package com.team.saver.market.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.entity.Account;
import com.team.saver.market.review.dto.ReviewResponse;
import com.team.saver.market.review.dto.ReviewStatistics;
import com.team.saver.market.review.dto.ReviewStatisticsResponse;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                        review.score,
                        reviewRecommender.count()
                )).from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .innerJoin(review.reviewer, account)
                .leftJoin(review.recommender, reviewRecommender)
                .groupBy(review)
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
                        review.score,
                        reviewRecommender.count()
                )).from(review)
                .innerJoin(review.market, market)
                .innerJoin(review.reviewer, account).on(account.email.eq(email))
                .leftJoin(review.recommender, reviewRecommender)
                .groupBy(review)
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

    @Override
    public List<ReviewResponse> findBestReview(Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(
                        ReviewResponse.class,
                        review.reviewId,
                        account.accountId,
                        account.email,
                        review.content,
                        market.marketId,
                        market.marketName,
                        review.score,
                        reviewRecommender.count()
                )).from(review)
                .innerJoin(review.market, market)
                .innerJoin(review.reviewer, account)
                .leftJoin(review.recommender, reviewRecommender)
                .groupBy(review)
                .orderBy(reviewRecommender.count().desc(), review.content.length().desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId) {
        List<Integer> numbers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());

        List<ReviewStatistics> results = numbers.stream().map(num -> {
            long count = jpaQueryFactory
                    .select(review.count())
                    .from(review)
                    .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                    .where(review.score.eq(num))
                    .fetchOne();

            return new ReviewStatistics(num, count);
        }).collect(Collectors.toList());

        double averageScore = jpaQueryFactory
                .select(review.score.avg())
                .from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .fetchOne();

        return ReviewStatisticsResponse.createDto(results, averageScore);
    }
}
