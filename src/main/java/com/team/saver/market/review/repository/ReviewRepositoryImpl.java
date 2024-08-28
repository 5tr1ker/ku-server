package com.team.saver.market.review.repository;

import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.market.order.entity.QOrder;

import com.team.saver.market.review.dto.*;
import com.team.saver.market.review.entity.QReview;
import com.team.saver.market.review.entity.QReviewRecommender;
import com.team.saver.market.review.entity.Review;
import com.team.saver.market.review.entity.ReviewRecommender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.market.order.entity.QOrder.order;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.market.review.entity.QReview.review;
import static com.team.saver.market.review.entity.QReviewImage.reviewImage;
import static com.team.saver.market.review.entity.QReviewRecommender.reviewRecommender;
import static com.team.saver.market.store.entity.QMarket.market;
import static com.team.saver.market.order.entity.QOrderMenu.orderMenu;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements CustomReviewRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReviewResponse> findByMarketId(String email, long marketId, SortType sortType) {
        QReviewRecommender qReviewRecommender = new QReviewRecommender("qReviewRecommender1");

        JPAQuery query = jpaQueryFactory
                .selectFrom(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .innerJoin(review.reviewer, account)
                .leftJoin(review.recommender, reviewRecommender).on(reviewRecommender.account.email.eq(email))
                .innerJoin(review.order, order)
                .leftJoin(order.orderMenus, orderMenu)
                .leftJoin(review.reviewImage, reviewImage).on(reviewImage.isDelete.eq(false))
                .where(review.isDelete.eq(false));

        setSortQuery(query, sortType);

        return (List<ReviewResponse>) query
                .transform(groupBy(review.reviewId)
                        .list(Projections.constructor(
                                ReviewResponse.class,
                                review.reviewId,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                review.content,
                                review.writeTime,
                                market.marketId,
                                market.marketName,
                                orderMenu.menuName,
                                review.score,
                                jpaQueryFactory.select(qReviewRecommender.count()).from(qReviewRecommender).where(qReviewRecommender.review.eq(review)),
                                reviewRecommender.isNotNull(),
                                list(Projections.constructor(ReviewImageResponse.class,
                                        reviewImage.reviewImageId,
                                        reviewImage.imageUrl).skipNulls())
                        ))
                );
    }

    private void setSortQuery(JPAQuery query, SortType sortType) {
        QReview qReview = new QReview("qReview2");

        if(sortType == sortType.RECENTLY_UPLOAD) {
            query.orderBy(review.writeTime.desc(), review.reviewId.desc());
        }
        else if(sortType == sortType.MOST_RECOMMEND) {
            query.orderBy(new OrderSpecifier<>(Order.DESC,
                            select(reviewRecommender.count())
                                    .from(qReview)
                                    .leftJoin(qReview.recommender, reviewRecommender)
                                    .where(qReview.eq(review))),
                    review.score.desc());
        } else if(sortType == sortType.HIGHEST_SCORE) {
            query.orderBy(review.score.desc(),
                    new OrderSpecifier<>(Order.DESC,
                            select(reviewRecommender.count())
                                    .from(qReview)
                                    .leftJoin(qReview.recommender, reviewRecommender)
                                    .where(qReview.eq(review))));
        } else if(sortType == sortType.LOWEST_SCORE) {
            query.orderBy(review.score.asc(),
                    new OrderSpecifier<>(Order.DESC,
                            select(reviewRecommender.count())
                                    .from(qReview)
                                    .leftJoin(qReview.recommender, reviewRecommender)
                                    .where(qReview.eq(review))));
        }
    }

    @Override
    public Optional<Review> findByReviewerAndReviewId(String reviewerEmail, long reviewId) {
        Review result = jpaQueryFactory.select(review)
                .from(review)
                .innerJoin(review.reviewer, account).on(account.email.eq(reviewerEmail))
                .where(review.reviewId.eq(reviewId).and(review.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<ReviewResponse> findByUserEmail(String email) {
        return jpaQueryFactory.selectFrom(review)
                .innerJoin(review.market, market)
                .innerJoin(review.reviewer, account).on(account.email.eq(email))
                .leftJoin(review.reviewImage, reviewImage).on(reviewImage.isDelete.eq(false))
                .leftJoin(review.recommender, reviewRecommender).on(reviewRecommender.account.email.eq(email))
                .innerJoin(review.order, order)
                .leftJoin(order.orderMenus, orderMenu)
                .orderBy(review.reviewId.desc())
                .where(review.isDelete.eq(false))
                .transform(groupBy(review.reviewId)
                        .list(Projections.constructor(
                                ReviewResponse.class,
                                review.reviewId,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                review.content,
                                review.writeTime,
                                market.marketId,
                                market.marketName,
                                orderMenu.menuName,
                                review.score,
                                jpaQueryFactory.select(reviewRecommender.count()).from(reviewRecommender).where(reviewRecommender.review.eq(review)),
                                reviewRecommender.isNotNull(),
                                list(Projections.constructor(ReviewImageResponse.class,
                                        reviewImage.reviewImageId,
                                        reviewImage.imageUrl).skipNulls())
                        ))
                );
    }

    @Override
    public Long findRecommenderCountByEmailAndReviewId(String email, long reviewId) {
        long result = jpaQueryFactory.select(reviewRecommender.count())
                .from(reviewRecommender)
                .innerJoin(reviewRecommender.account, account).on(account.email.eq(email))
                .innerJoin(reviewRecommender.review, review).on(review.reviewId.eq(reviewId).and(review.isDelete.eq(false)))
                .fetchOne();

        return result;
    }

    @Override
    public List<ReviewRecommender> findRecommenderByEmailAndReviewId(String email, long reviewId) {
        List<ReviewRecommender> result = jpaQueryFactory.select(reviewRecommender)
                .from(reviewRecommender)
                .innerJoin(reviewRecommender.account, account).on(account.email.eq(email))
                .innerJoin(reviewRecommender.review, review).on(review.reviewId.eq(reviewId).and(review.isDelete.eq(false)))
                .fetch();

        return result;
    }

    @Override
    public List<ReviewResponse> findBestReview(String email, long marketId, Pageable pageable) {
        JPAQueryBase query = jpaQueryFactory.selectFrom(review)
                .innerJoin(review.reviewer, account)
                .leftJoin(review.reviewImage, reviewImage).on(reviewImage.isDelete.eq(false))
                .leftJoin(review.recommender, reviewRecommender).on(reviewRecommender.account.email.eq(email))
                .innerJoin(review.order, order)
                .leftJoin(order.orderMenus, orderMenu)
                .orderBy(new OrderSpecifier<>(Order.DESC, jpaQueryFactory.select(reviewRecommender.count())
                                .from(reviewRecommender)
                                .where(reviewRecommender.review.eq(review))),
                        review.content.length().desc())
                .where(review.isDelete.eq(false));

        if(marketId == 0) {
            query.innerJoin(review.market, market);
        } else {
            query.innerJoin(review.market, market).on(market.marketId.eq(marketId));
        }

        return ((List<ReviewResponse>) query.transform(groupBy(review.reviewId)
                        .list(Projections.constructor(
                                ReviewResponse.class,
                                review.reviewId,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                review.content,
                                review.writeTime,
                                market.marketId,
                                market.marketName,
                                orderMenu.menuName,
                                review.score,
                                jpaQueryFactory.select(reviewRecommender.count()).from(reviewRecommender).where(reviewRecommender.review.eq(review)),
                                reviewRecommender.isNotNull(),
                                list(Projections.constructor(ReviewImageResponse.class,
                                        reviewImage.reviewImageId,
                                        reviewImage.imageUrl).skipNulls())
                        ))
                )).stream()
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    @Override
    public long countReviewByEmail(String email) {
        return jpaQueryFactory.select(review.count())
                .from(review)
                .innerJoin(review.reviewer, account).on(account.email.eq(email))
                .where(review.isDelete.eq(false))
                .fetchOne();
    }

    @Override
    public ReviewStatisticsResponse findReviewStatisticsByMarketId(long marketId) {
        List<Integer> numbers = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());

        List<ReviewStatisticsData> results = numbers.stream().map(num -> {
            long count = jpaQueryFactory
                    .select(review.count())
                    .from(review)
                    .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                    .where(review.score.eq(num).and(review.isDelete.eq(false)))
                    .fetchOne();

            return new ReviewStatisticsData(num, count);
        }).collect(Collectors.toList());

        double averageScore = jpaQueryFactory
                .select(review.score.avg())
                .from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .where(review.isDelete.eq(false))
                .fetchOne();

        long totalReviewCount = jpaQueryFactory
                .select(review.count())
                .from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .where(review.isDelete.eq(false))
                .fetchOne();

        return ReviewStatisticsResponse.createDto(results, averageScore, totalReviewCount);
    }

    @Override
    public List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId) {
        return jpaQueryFactory
                .selectFrom(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .leftJoin(review.reviewImage, reviewImage)
                .where(review.isDelete.eq(false).and(reviewImage.imageUrl.isNotNull()))
                .transform(groupBy(review.reviewId).list(
                        Projections.constructor(PhotoReviewResponse.class,
                                list(Projections.constructor(ReviewImageResponse.class, reviewImage.reviewImageId, reviewImage.imageUrl))
                                , review.reviewId)
                ));
    }

    @Override
    public List<PhotoReviewResponse> findAllReviewImageByMarketId(long marketId, long size) {
        return jpaQueryFactory
                .selectFrom(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .leftJoin(review.reviewImage, reviewImage)
                .where(review.isDelete.eq(false).and(reviewImage.imageUrl.isNotNull()))
                .limit(size)
                .transform(groupBy(review.reviewId).list(
                        Projections.constructor(PhotoReviewResponse.class,
                                list(Projections.constructor(ReviewImageResponse.class, reviewImage.reviewImageId, reviewImage.imageUrl))
                                , review.reviewId)
                ));
    }

    @Override
    public Optional<ReviewResponse> findDetailByReviewId(String email, long reviewId) {
        List<ReviewResponse> result = jpaQueryFactory.selectFrom(review)
                .innerJoin(review.market, market)
                .innerJoin(review.reviewer, account)
                .leftJoin(review.reviewImage, reviewImage).on(reviewImage.isDelete.eq(false))
                .leftJoin(review.recommender, reviewRecommender).on(reviewRecommender.account.email.eq(email))
                .innerJoin(review.order, order)
                .leftJoin(order.orderMenus, orderMenu)
                .where(review.reviewId.eq(reviewId).and(review.isDelete.eq(false)))
                .transform(groupBy(review.reviewId)
                        .list(Projections.constructor(
                                ReviewResponse.class,
                                review.reviewId,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                review.content,
                                review.writeTime,
                                market.marketId,
                                market.marketName,
                                orderMenu.menuName,
                                review.score,
                                jpaQueryFactory.select(reviewRecommender.count()).from(reviewRecommender).where(reviewRecommender.review.eq(review)),
                                reviewRecommender.isNotNull(),
                                list(Projections.constructor(ReviewImageResponse.class,
                                        reviewImage.reviewImageId,
                                        reviewImage.imageUrl).skipNulls())
                        ))
                );

        if (result.size() == 0) {
            return Optional.empty();
        }

        return Optional.ofNullable(result.get(0));
    }

    @Override
    public Optional<ReviewStatisticResponse> findReviewStatisticsByEmail(String email) {
        QReview qReview = new QReview("qReview1");
        QOrder qOrder = new QOrder("qOrder1");

        ReviewStatisticResponse result = jpaQueryFactory.select(Projections.constructor(
                        ReviewStatisticResponse.class,
                        select(qOrder.count()).from(qOrder).leftJoin(qOrder.review, qReview).where(qReview.isNull()),
                        review.count()
                )).from(order)
                .innerJoin(order.review, review)
                .innerJoin(review.reviewer, account).on(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public long findPhotoReviewCountByMarketId(long marketId) {
        return jpaQueryFactory.select(reviewImage.count())
                .from(review)
                .innerJoin(review.market, market).on(market.marketId.eq(marketId))
                .leftJoin(review.reviewImage, reviewImage)
                .where(review.isDelete.eq(false))
                .fetchOne();
    }
}
