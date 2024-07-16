package com.team.saver.attraction.promotion.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.promotion.dto.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.team.saver.attraction.promotion.entity.QPromotion.promotion;
import static com.team.saver.attraction.promotion.entity.QPromotionTag.promotionTag;
import static com.team.saver.attraction.promotion.entity.QPromotionTagRelationShip.promotionTagRelationShip;

@RequiredArgsConstructor
public class PromotionRepositoryImpl implements CustomPromotionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PromotionResponse> findByRecommend(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(promotion)
                .leftJoin(promotion.promotionTagRelationShips, promotionTagRelationShip)
                .leftJoin(promotionTagRelationShip.promotionTag, promotionTag)
                .transform(groupBy(promotion.promotionId).list(
                        Projections.constructor(
                                PromotionResponse.class,
                                promotion.promotionId,
                                promotion.imageUrl,
                                list(
                                        promotionTag.tagContent
                                ),
                                promotion.introduce
                        )
                ));
    }
}
