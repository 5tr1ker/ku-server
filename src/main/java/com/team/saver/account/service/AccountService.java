package com.team.saver.account.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUNT_USER;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getProfile(UserDetails userDetails) {
        Account account = accountRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUNT_USER));

        return account;
    }

}
