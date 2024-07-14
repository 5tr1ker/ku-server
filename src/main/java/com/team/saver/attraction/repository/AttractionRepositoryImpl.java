package com.team.saver.attraction.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.dto.AttractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.team.saver.attraction.entity.QAttraction.attraction;
import static com.team.saver.attraction.entity.QAttractionTag.attractionTag;
import static com.team.saver.attraction.entity.QAttractionTagRelationShip.attractionTagRelationShip;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements CustomAttractionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AttractionResponse> findByRecommend(Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(attraction)
                .leftJoin(attraction.attractionTagRelationShips, attractionTagRelationShip)
                .leftJoin(attractionTagRelationShip.attractionTag, attractionTag)
                .transform(groupBy(attraction.attractionId).list(
                        Projections.constructor(
                                AttractionResponse.class,
                                attraction.attractionId,
                                attraction.imageUrl,
                                list(
                                        attractionTag.tagContent
                                ),
                                attraction.title,
                                attraction.introduce
                        )
                ));
    }
}
