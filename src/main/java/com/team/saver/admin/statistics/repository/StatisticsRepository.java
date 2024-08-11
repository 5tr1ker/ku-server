package com.team.saver.admin.statistics.repository;

import com.team.saver.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Account ,Long>, CustomStatisticsRepository {
}
