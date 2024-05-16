package com.team.saver.history.repository;

import com.team.saver.account.entity.Account;
import com.team.saver.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long>, CustomHistoryRepository {

    void deleteAllByAccount(Account account);

    Optional<History> findByAccountAndContent(Account account, String content);

}