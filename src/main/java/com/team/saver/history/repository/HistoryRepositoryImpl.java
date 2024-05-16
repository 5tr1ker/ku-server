package com.team.saver.history.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.history.dto.HistoryResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HistoryRepositoryImpl implements CustomHistoryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteHistoryByAccountAndHistoryId(CurrentUser currentUser, long historyId) {

    }

    @Override
    public List<HistoryResponse> findAllByAccount(CurrentUser currentUser) {
        return null;
    }
}
