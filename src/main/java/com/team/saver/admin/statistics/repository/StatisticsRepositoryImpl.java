package com.team.saver.admin.statistics.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.admin.statistics.dto.AdminTodoResponse;
import lombok.RequiredArgsConstructor;

import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.report.entity.QReport.report;
import static com.team.saver.socket.entity.QChatRoom.chatRoom;

@RequiredArgsConstructor
public class StatisticsRepositoryImpl implements CustomStatisticsRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public AdminTodoResponse findAdminTodo() {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                AdminTodoResponse.class,
                                select(partnerRequest.count()).from(partnerRequest),
                                chatRoom.count(),
                                select(report.count()).from(report).where(report.isRead.eq(false))
                        )
                ).from(chatRoom)
                .where(chatRoom.isRead.eq(false))
                .fetchOne();
    }
}
