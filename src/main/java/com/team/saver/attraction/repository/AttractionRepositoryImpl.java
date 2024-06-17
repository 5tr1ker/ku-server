package com.team.saver.attraction.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.dto.AttractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.saver.attraction.entity.QAttraction.attraction;
import static com.team.saver.attraction.entity.QAttractionTag.attractionTag;
import static com.team.saver.attraction.entity.QAttractionTagRelationShip.attractionTagRelationShip;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements CustomAttractionRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AttractionResponse> findByRecommend(Pageable pageable) {
        List<AttractionResponse> result = jpaQueryFactory.select(Projections.constructor(AttractionResponse.class,
                        attraction.attractionId,
                        attraction.imageUrl,
                        Projections.list(
                                attractionTag.tagContent
                        ),
                        attraction.description
                ))
                .from(attraction)
                .leftJoin(attraction.attractionTagRelationShips, attractionTagRelationShip)
                .leftJoin(attractionTagRelationShip.attractionTag, attractionTag)
                .fetch();

        Map<Long, AttractionResponse> attractionMap = new HashMap<>();
        for (AttractionResponse response : result) {
            if (!attractionMap.containsKey(response.getAttractionId())) {
                AttractionResponse newResponse = new AttractionResponse(
                        response.getAttractionId(),
                        response.getImageUrl(),
                        new ArrayList<>(),
                        response.getDescription()
                );
                attractionMap.put(response.getAttractionId(), newResponse);
            }

            attractionMap.get(response.getAttractionId()).getTag().add(response.getTag().get(0));
        }

        return new ArrayList<>(attractionMap.values());
    }
}
