package com.team.saver.account.service;

import com.team.saver.account.dto.SchoolCertRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final MailService mailService;

    public Account getProfile(CurrentUser currentUser) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return account;
    }

    public void sendCodeInOrderToCertStudent(CurrentUser currentUser, SchoolCertRequest request) {
        if(!isStudentEmail(request.getSchoolEmail())) {
            throw new CustomRuntimeException(NOT_STUDENT_EMAIL);
        }

        mailService.sendMail(request.getSchoolEmail());
    }

    @Transactional
    public void checkCodeInOrderToCertStudent(CurrentUser currentUser, MailRequest request) {
        mailService.checkVerificationCode(request);
        isExistsSchoolEmail(request.getSchoolEmail());

        updateRoleToStudent(currentUser, request);
    }

    protected void updateRoleToStudent(CurrentUser currentUser, MailRequest request) {
        Account account = getProfile(currentUser);

        updateToleToStudent(account, request.getSchoolEmail());
    }

    private void updateToleToStudent(Account account, String schoolEmail) {
        if(isStudentEmail(schoolEmail)) {
            account.updateSchoolEmail(schoolEmail);

            account.updateRoleToStudent();
            return;
        }

        throw new CustomRuntimeException(NOT_STUDENT_EMAIL);
    }

    private void isExistsSchoolEmail(String email) {
        Account account = accountRepository.findBySchoolEmail(email).orElse(null);
        if(account == null) {
            return;
        }

        throw new CustomRuntimeException(EXIST_SCHOOL_EMAIL, account.getEmail(), account.getEmail());
    }

    private boolean isStudentEmail(String email) {
        if(email.endsWith("@kku.ac.kr")) {
            return true;
        }

        return false;
    }


}
