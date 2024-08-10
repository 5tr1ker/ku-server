package com.team.saver.admin.visitant.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.admin.visitant.dto.VisitantResponse;
import lombok.RequiredArgsConstructor;

import static com.team.saver.admin.visitant.domain.QVisitant.visitant;
import java.util.List;

@RequiredArgsConstructor
public class VisitantRepositoryImpl implements CustomVisitantRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<VisitantResponse> findVisitantCountByVisitDate() {
        return queryFactory.select(Projections.constructor(VisitantResponse.class
                        , visitant.count(), visitant.visitDate ))
                .from(visitant)
                .groupBy(visitant.visitDate)
                .orderBy(visitant.visitDate.asc())
                .limit(14)
                .fetch();
    }
}
