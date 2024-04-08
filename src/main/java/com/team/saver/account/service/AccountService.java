package com.team.saver.account.service;

import com.team.saver.account.dto.SignUpRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUNT_USER;
import static com.team.saver.common.dto.ErrorMessage.PASSWORD_NOT_MATCH;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void signUp(SignUpRequest request) {
        Account account = Account.createAccount(request);

        accountRepository.save(account);
    }

    public void signIn(SignUpRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(NOT_FOUNT_USER.getMessage()));

        if(account.getPassword().equals(request.getPassword())) {
            throw new RuntimeException(PASSWORD_NOT_MATCH.getMessage());
        }
    }

    public Account getProfile(UserDetails userDetails) {
        Account account = accountRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException(NOT_FOUNT_USER.getMessage()));

        return account;
    }

}
