package com.team.saver.admin.statistics.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements CustomStatisticsRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
