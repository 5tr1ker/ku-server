package com.team.saver.attraction.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.dto.AttractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.attraction.entity.QAttraction.attraction;
import static com.team.saver.attraction.entity.QAttractionTag.attractionTag;
import static com.team.saver.attraction.entity.QAttractionTagRelationShip.attractionTagRelationShip;

import java.util.List;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements CustomAttractionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AttractionResponse> findByRecommend(Pageable pageable) {
        return jpaQueryFactory.select(Projections.constructor(AttractionResponse.class,
                        attraction.attractionId,
                        attraction.imageUrl,
                        attractionTag,
                        attraction.description
                ))
                .from(attraction)
                .leftJoin(attraction.attractionTagRelationShips, attractionTagRelationShip)
                .leftJoin(attractionTagRelationShip.attractionTag, attractionTag)
                .fetch();
    }
}
