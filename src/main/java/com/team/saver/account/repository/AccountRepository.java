package com.team.saver.account.repository;

import com.team.saver.account.entity.Account;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.history.dto.HistoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findBySchoolEmail(String email);
}
