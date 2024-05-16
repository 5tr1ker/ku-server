package com.team.saver.history.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.history.dto.HistoryDeleteRequest;
import com.team.saver.history.dto.HistoryRequest;
import com.team.saver.history.dto.HistoryResponse;
import com.team.saver.history.entity.History;
import com.team.saver.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;

    public List<HistoryResponse> findAllByAccount(CurrentUser currentUser) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return historyRepository.findAllByAccount(account);
    }

    @Transactional
    public void addHistoryByAccount(CurrentUser currentUser, HistoryRequest historyRequest) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        History history = historyRepository.findByAccountAndContent(account, historyRequest.getContent())
                .orElseGet(() -> History.createEntity(account, historyRequest.getContent()));

        history.updateTime();
        historyRepository.save(history);
    }

    @Transactional
    public void deleteAllHistoryByAccount(CurrentUser currentUser) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        historyRepository.deleteAllByAccount(account);
    }

    @Transactional
    public void deleteHistoryByAccountAndHistoryId(CurrentUser currentUser, HistoryDeleteRequest historyRequest) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        historyRepository.deleteHistoryByAccountAndHistoryId(account, historyRequest.getHistoryId());
    }

}
