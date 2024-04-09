package com.team.saver.account.controller;

import com.team.saver.account.dto.SignUpRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody SignUpRequest request) {
        accountService.signUp(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody SignUpRequest request, HttpSession session) {
        accountService.signIn(request, session);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Account result = accountService.getProfile(userDetails);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication) {
        System.out.println("/test/login==========");
        Account principalDetails = (Account) authentication.getPrincipal();
        System.out.println("authentication : {}" +  principalDetails.getEmail());
        return "세션 정보 확인하기";
    }

}
