package com.team.saver.account.controller;

import com.team.saver.account.dto.SchoolCertRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.mail.dto.MailRequest;
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
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final OAuthService authService;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-in")
    @Operation(summary = "테스트를 위한 로그인 API")
    public ResponseEntity signIn(@RequestParam String email, HttpServletResponse response) {
        Account account = accountRepository.findByEmail(email).get();
        authService.updateLoginCount(account);
        Token result = jwtTokenProvider.login(response, account.getEmail(), account.getRole());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API")
    public ResponseEntity logout(HttpServletResponse response) {
        jwtTokenProvider.deleteJwtCookieFromResponse(response);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    @Operation(summary = "[ 로그인 ] 사용자 정보 가져오기")
    public ResponseEntity getProfile(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        Account result = accountService.getProfile(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/cert/student/send-mail")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 전송 API")
    public ResponseEntity sendCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                     @RequestBody SchoolCertRequest request) {
        accountService.sendCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/re-cert/student/send-mail")
    @Operation(summary = "[ 로그인 ] 새로운 계정을 대상으로 기존에 인증된 학생 메일 재전송 API ( 기존과 다르게 학교 이메일 존재 여부를 확인 안함 )")
    public ResponseEntity sendCodeInOrderToReCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                       @RequestBody SchoolCertRequest request) {
        accountService.sendCodeInOrderToReCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/cert/student/code-check")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 확인 API")
    public ResponseEntity checkCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                              @RequestBody MailRequest request) {
        accountService.checkCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

}
