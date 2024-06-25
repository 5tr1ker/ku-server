package com.team.saver.account.controller;

import com.team.saver.account.dto.SchoolCertRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.mail.dto.MailSendRequest;
import com.team.saver.oauth.service.OAuthService;
import com.team.saver.security.jwt.dto.Token;
import com.team.saver.security.jwt.support.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OAuthService authService;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/v1/accounts/sign-in")
    @Operation(summary = "테스트를 위한 로그인 API")
    public ResponseEntity signIn(@RequestParam String email, HttpServletResponse response) {
        Account account = accountRepository.findByEmail(email).get();
        authService.updateLoginCount(account);
        Token result = jwtTokenProvider.login(response, account.getEmail(), account.getRole());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/accounts/logout")
    @Operation(summary = "로그아웃 API")
    public ResponseEntity logout(HttpServletResponse response) {
        jwtTokenProvider.deleteJwtCookieFromResponse(response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/accounts/profiles")
    @Operation(summary = "[ 로그인 ] 사용자 정보 가져오기")
    public ResponseEntity getProfile(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        Account result = accountService.getProfile(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/accounts/certs/students/send-mail")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 전송 API")
    public ResponseEntity sendCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                     @RequestBody SchoolCertRequest request) {
        accountService.sendCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/v1/accounts/certs/students/code-check")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 확인 API")
    public ResponseEntity checkCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                              @RequestBody MailSendRequest request) {
        accountService.checkCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

}
