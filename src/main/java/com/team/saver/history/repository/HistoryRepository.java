package com.team.saver.history.repository;

import com.team.saver.account.entity.Account;
import com.team.saver.history.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long>, CustomHistoryRepository {

    void deleteAllByAccount(Account account);

}
