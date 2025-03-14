package com.team.saver.security.jwt.support;

import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_USER));
    }
}