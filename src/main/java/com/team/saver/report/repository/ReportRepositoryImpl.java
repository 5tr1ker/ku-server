package com.team.saver.report.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.report.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.report.entity.QReport.report;

import java.util.List;

@RequiredArgsConstructor
public class ReportRepositoryImpl implements CustomReportRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReportResponse> findAllReport(Pageable pageable) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                ReportResponse.class,
                                report.reportId,
                                report.title,
                                report.content,
                                report.createTime,
                                report.isRead
                        )
                ).from(report)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
