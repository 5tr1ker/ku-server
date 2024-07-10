package com.team.saver.account.repository;

import com.team.saver.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, CustomAccountRepository {

    Optional<Account> findByEmail(String email);

    Optional<Account> findBySchoolEmail(String email);

}
