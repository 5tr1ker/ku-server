package com.team.saver.attraction.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.attraction.dto.AttractionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.saver.attraction.entity.QAttraction.attraction;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements CustomAttractionRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<AttractionResponse> getAttraction(Pageable pageable, OrderSpecifier orderSpecifier) {
        return jpaQueryFactory.select(Projections.constructor(
                        AttractionResponse.class,
                        attraction.attractionId,
                        attraction.attractionName,
                        attraction.attractionDescription,
                        attraction.imageUrl,
                        attraction.openTime,
                        attraction.closeTime,
                        attraction.eventMessage,
                        attraction.locationX,
                        attraction.locationY
                )).from(attraction)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<AttractionResponse> searchAttraction(Pageable pageable, OrderSpecifier orderSpecifier, String keyWord) {
        return jpaQueryFactory.select(Projections.constructor(
                        AttractionResponse.class,
                        attraction.attractionId,
                        attraction.attractionName,
                        attraction.attractionDescription,
                        attraction.imageUrl,
                        attraction.openTime,
                        attraction.closeTime,
                        attraction.eventMessage,
                        attraction.locationX,
                        attraction.locationY
                )).from(attraction)
                .where(attraction.attractionName.contains(keyWord).or(attraction.attractionDescription.contains(keyWord)))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
