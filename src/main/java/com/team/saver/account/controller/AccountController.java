package com.team.saver.account.controller;

import com.team.saver.account.dto.*;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OAuthService authService;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/v1/accounts/sign-in")
    @Operation(summary = "테스트를 위한 로그인 API ( 0 ) ")
    public ResponseEntity signIn(@RequestParam String email, HttpServletResponse response) {
        Account account = accountRepository.findByEmail(email).get();
        authService.updateLoginCount(account);
        Token result = jwtTokenProvider.login(response, account.getEmail(), account.getRole());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/accounts/logout")
    @Operation(summary = "로그아웃 API ( 1 ) ")
    public ResponseEntity logout(HttpServletResponse response) {
        jwtTokenProvider.deleteJwtCookieFromResponse(response);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/v1/accounts/tokens")
    @Operation(summary = "[ 로그인 ] 토큰 재발급 API ( 2 ) ")
    public ResponseEntity reissueToken(HttpServletResponse response, HttpServletRequest request) {
        Token token = jwtTokenProvider.reissueToken(response, request);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/v1/accounts/profiles")
    @Operation(summary = "[ 로그인 ] 사용자 정보 가져오기 ( 3 ) ")
    public ResponseEntity getProfile(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        AccountResponse result = accountService.findAccountDetail(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/accounts/my-page")
    @Operation(summary = "[ 로그인 ] 사용자 마이페이지 정보 가져오기 ( 4 ) ")
    public ResponseEntity getMyPageInfo(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        MyPageResponse result = accountService.getMyPageInfo(currentUser);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/v1/accounts")
    @Operation(summary = "[ 로그인 ] 계정 탈퇴 ( 5 ) ")
    public ResponseEntity deleteAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        accountService.deleteAccount(currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/v1/accounts")
    @Operation(summary = "[ 로그인 ] 사용자 계정 정보 수정 ( 6 ) ")
    public ResponseEntity updateAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                        @RequestBody AccountUpdateRequest request) {
        accountService.updateAccount(currentUser, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/v1/accounts/settings")
    @Operation(summary = "[ 로그인 ] 사용자 설정 정보 수정 ( 7 ) ")
    public ResponseEntity updateAccountSetting(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @RequestBody AccountNotificationSetting request) {
        accountService.updateAccountSetting(currentUser, request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/v1/accounts/images")
    @Operation(summary = "[ 로그인 ] 사용자 계정 이미지 수정 ( 8 ) ")
    public ResponseEntity updateAccountImage(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                             @RequestPart MultipartFile image) {
        accountService.updateAccountImage(currentUser, image);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/accounts/certs/students/send-mail")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 전송 API ( 9 ) ")
    public ResponseEntity sendCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                       @RequestBody SchoolCertRequest request) {
        accountService.sendCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/v1/accounts/certs/students/code-check")
    @Operation(summary = "[ 로그인 ] 학생으로 권한 변경을 위한 메일 확인 API ( 10 ) ")
    public ResponseEntity checkCodeInOrderToCertStudent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                        @RequestBody MailSendRequest request) {
        accountService.checkCodeInOrderToCertStudent(currentUser, request);

        return ResponseEntity.ok().build();
    }

}
