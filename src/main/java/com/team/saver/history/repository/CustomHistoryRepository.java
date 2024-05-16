package com.team.saver.history.repository;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.history.dto.HistoryResponse;

import java.util.List;

public interface CustomHistoryRepository {

    void deleteHistoryByAccountAndHistoryId(CurrentUser currentUser, long historyId);

    List<HistoryResponse> findAllByAccount(CurrentUser currentUser);
}
