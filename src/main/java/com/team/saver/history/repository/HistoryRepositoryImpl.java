package com.team.saver.history.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.entity.Account;
import com.team.saver.history.dto.HistoryResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.history.entity.QHistory.history;
import static com.team.saver.account.entity.QAccount.account;

import java.util.List;

@RequiredArgsConstructor
public class HistoryRepositoryImpl implements CustomHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteHistoryByAccountAndHistoryId(Account accountData, long historyId) {
        jpaQueryFactory.delete(history)
                .where(history.historyId.eq(
                        select(history.historyId)
                                .from(history)
                                .innerJoin(history.account, account).on(account.eq(accountData))
                                .where(history.historyId.eq(historyId))
                ))
                .execute();
    }

    @Override
    public List<HistoryResponse> findAllByAccount(Account accountData) {
        return jpaQueryFactory.select(
                        Projections.constructor(HistoryResponse.class,
                                history.content
                        )).from(history)
                .innerJoin(history.account, account).on(account.eq(accountData))
                .orderBy(history.localDateTime.desc())
                .fetch();
    }
}
