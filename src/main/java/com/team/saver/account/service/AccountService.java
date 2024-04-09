package com.team.saver.account.service;

import com.team.saver.account.dto.SignUpRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        if(accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException(ALREADY_EXISTS_USER.getMessage());
        }
        String encodePassword = bCryptPasswordEncoder.encode(request.getPassword());

        Account account = Account.createAccount(request, encodePassword);

        accountRepository.save(account);
    }

    public Authentication getAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public void signIn(SignUpRequest request, HttpSession session) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException(NOT_FOUNT_USER.getMessage()));

        Authentication authentication = getAuthentication(request.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

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
