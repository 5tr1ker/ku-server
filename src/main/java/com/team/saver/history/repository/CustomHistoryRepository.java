package com.team.saver.history.repository;

import com.team.saver.history.dto.HistoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomHistoryRepository {

    long deleteHistoryByEmailAndHistoryId(String email, long historyId);

    List<HistoryResponse> findAllByEmail(String email, Pageable pageable);
}
