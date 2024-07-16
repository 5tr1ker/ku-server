package com.team.saver.promotion.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.promotion.dto.PromotionResponse;
import com.team.saver.promotion.entity.PromotionLocation;
import com.team.saver.promotion.entity.QPromotion;
import com.team.saver.promotion.entity.QPromotionTag;
import com.team.saver.promotion.entity.QPromotionTagRelationShip;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class PromotionRepositoryImpl implements CustomPromotionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PromotionResponse> findByRecommend(Pageable pageable, PromotionLocation location) {
        return jpaQueryFactory
                .selectFrom(QPromotion.promotion)
                .leftJoin(QPromotion.promotion.promotionTagRelationShips, QPromotionTagRelationShip.promotionTagRelationShip)
                .leftJoin(QPromotionTagRelationShip.promotionTagRelationShip.promotionTag, QPromotionTag.promotionTag)
                .where(QPromotion.promotion.promotionLocation.eq(location))
                .transform(groupBy(QPromotion.promotion.promotionId).list(
                        Projections.constructor(
                                PromotionResponse.class,
                                QPromotion.promotion.promotionId,
                                QPromotion.promotion.imageUrl,
                                GroupBy.list(
                                        QPromotionTag.promotionTag.tagContent
                                ),
                                QPromotion.promotion.introduce
                        )
                ));
    }
}
