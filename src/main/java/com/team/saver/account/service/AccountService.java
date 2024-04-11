package com.team.saver.account.service;

import com.team.saver.account.dto.SignUpRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserType;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.security.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionManager sessionManager;

    @Transactional
    public void signUp(SignUpRequest request) {
        if(accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException(ALREADY_EXISTS_USER.getMessage());
        }
        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());

        Account account = Account.createAccount(request, encodePassword);

        accountRepository.save(account);
    }

    public void signIn(SignUpRequest request, HttpSession session) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(NOT_FOUNT_USER.getMessage()));

        isValidSignIn(request, account);

        sessionManager.addSession(request.getEmail(), session);
    }

    private void isValidSignIn(SignUpRequest request, Account account) {
        if(!account.getType().equals(UserType.GENERATE)) {
            throw new RuntimeException(NOT_VALID_GENERAL_USER.getMessage());
        }

        if(!bCryptPasswordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException(PASSWORD_NOT_MATCH.getMessage());
        }
    }

    public Account getProfile(UserDetails userDetails) {
        Account account = accountRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException(NOT_FOUNT_USER.getMessage()));

        return account;
    }

}
