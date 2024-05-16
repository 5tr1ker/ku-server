package com.team.saver.history.repository;

import com.team.saver.account.entity.Account;
import com.team.saver.history.dto.HistoryResponse;

import java.util.List;

public interface CustomHistoryRepository {

    void deleteHistoryByAccountAndHistoryId(Account account, long historyId);

    List<HistoryResponse> findAllByAccount(Account account);
}
