package com.team.saver.account.controller;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.security.util.SessionManager;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUNT_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;
    private final SessionManager sessionManager;
    @PostMapping("/sign-in")
    @Operation(summary = "테스트를 위한 로그인 API")
    public ResponseEntity signIn(@RequestParam String email, HttpSession session) {
        sessionManager.addSession(email, session);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    @Operation(summary = "사용자 정보 가져오기")
    public ResponseEntity getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Account result = accountService.getProfile(userDetails);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/test/login")
    @Operation(summary = "테스트 API 곧 삭제 예정")
    public @ResponseBody String testLogin(Authentication authentication) {
        System.out.println("/test/login==========");
        Account principalDetails = (Account) authentication.getPrincipal();
        System.out.println("authentication : {}" +  principalDetails.getEmail());
        return "세션 정보 확인하기";
    }

}
