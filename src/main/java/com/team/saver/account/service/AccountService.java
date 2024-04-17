package com.team.saver.account.service;

import com.team.saver.account.dto.UpdateRoleRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getProfile(CurrentUser currentUser) {
        Account account = accountRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));

        return account;
    }

    @Transactional
    public void updateRole(CurrentUser currentUser, UpdateRoleRequest request) {
        Account account = getProfile(currentUser);

        if(request.getUserRole() == UserRole.STUDENT) {
            updateToleToStudent(account);

            return;
        }
        else if(request.getUserRole() == UserRole.PARTNER) {
            account.updateRoleToPartner();

            return;
        }
        else if(request.getUserRole() == UserRole.ADMIN) {
            account.updateRoleToAdmin();

            return;
        }

        throw new CustomRuntimeException(NOT_FOUND_ROLE);
    }

    private void updateToleToStudent(Account account) {
        if(isStudent(account.getEmail())) {
            account.updateRoleToStudent();
        }

        throw new CustomRuntimeException(NOT_STUDENT);
    }

    private boolean isStudent(String email) {
        if(email.endsWith("@kku.ac.kr")) {
            return true;
        }

        return false;
    }

}
