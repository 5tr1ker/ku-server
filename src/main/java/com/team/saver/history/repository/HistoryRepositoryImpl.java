package com.team.saver.history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.history.dto.HistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.history.entity.QHistory.history;

@RequiredArgsConstructor
public class HistoryRepositoryImpl implements CustomHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long deleteHistoryByEmailAndHistoryId(String email, long historyId) {
        Long result = jpaQueryFactory.select(history.historyId)
                .from(history)
                .innerJoin(history.account, account).on(account.email.eq(email))
                .where(history.historyId.eq(historyId))
                .fetchOne();

        if(result == null) {
            return 0;
        }

        return jpaQueryFactory.delete(history)
                .where(history.historyId.eq(result))
                .execute();
    }

    @Override
    public List<HistoryResponse> findAllByEmail(String email, Pageable pageable) {
        return jpaQueryFactory.select(
                        Projections.constructor(HistoryResponse.class,
                                history.historyId,
                                history.content
                        )).from(history)
                .innerJoin(history.account, account).on(account.email.eq(email))
                .orderBy(history.localDateTime.desc())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
