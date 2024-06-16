package com.team.saver.attraction.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttractionRepositoryImpl implements CustomAttractionRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
