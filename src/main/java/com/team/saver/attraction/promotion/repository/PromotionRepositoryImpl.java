package com.team.saver.attraction.promotion.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.promotion.dto.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.team.saver.attraction.promotion.entity.QAttraction.attraction;
import static com.team.saver.attraction.promotion.entity.QAttractionTag.attractionTag;
import static com.team.saver.attraction.promotion.entity.QAttractionTagRelationShip.attractionTagRelationShip;

@RequiredArgsConstructor
public class PromotionRepositoryImpl implements CustomPromotionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PromotionResponse> findByRecommend(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(attraction)
                .leftJoin(attraction.attractionTagRelationShips, attractionTagRelationShip)
                .leftJoin(attractionTagRelationShip.attractionTag, attractionTag)
                .transform(groupBy(attraction.attractionId).list(
                        Projections.constructor(
                                PromotionResponse.class,
                                attraction.attractionId,
                                attraction.imageUrl,
                                list(
                                        attractionTag.tagContent
                                ),
                                attraction.introduce
                        )
                ));
    }
}
