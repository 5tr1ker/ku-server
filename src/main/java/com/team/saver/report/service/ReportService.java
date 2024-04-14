package com.team.saver.report.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.report.dto.ReportRequest;
import com.team.saver.report.entity.Report;
import com.team.saver.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void addReport(CurrentUser currentUser, ReportRequest request) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        Report result = Report.createEntity(account, request.getContent());
        reportRepository.save(result);
    }

}
