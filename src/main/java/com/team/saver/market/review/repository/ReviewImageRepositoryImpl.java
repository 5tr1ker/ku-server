package com.team.saver.market.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.team.saver.market.review.entity.QReviewImage.reviewImage;
import java.util.List;

@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements CustomReviewImageRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteById(List<Long> reviewImageIds) {
        jpaQueryFactory.update(reviewImage)
                .set(reviewImage.isDelete, true)
                .where(reviewImage.reviewImageId.in(reviewImageIds))
                .execute();
    }
}
