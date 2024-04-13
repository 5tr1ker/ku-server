package com.team.saver.account.controller;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.security.jwt.dto.Token;
import com.team.saver.security.jwt.support.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/sign-in")
    @Operation(summary = "테스트를 위한 로그인 API")
    public ResponseEntity signIn(@RequestParam String email) {
        Account account = accountRepository.findByEmail(email).get();
        Token result = jwtTokenProvider.createJwtToken(account.getEmail(), account.getRole());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile")
    @Operation(summary = "사용자 정보 가져오기")
    public ResponseEntity getProfile(@LogIn CurrentUser currentUser) {
        Account result = accountService.getProfile(currentUser);

        return ResponseEntity.ok(result);
    }

}
