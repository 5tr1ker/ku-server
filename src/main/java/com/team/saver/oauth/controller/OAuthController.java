package com.team.saver.oauth.controller;

import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.dto.OAuthTransferRequest;
import com.team.saver.oauth.service.OAuthService;
import com.team.saver.security.jwt.dto.Token;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/sign-in")
    @Operation(summary = "OAuth 로그인")
    public ResponseEntity signIn(HttpServletResponse response, @RequestBody OAuthRequest request) {
        Token result = oAuthService.SignInOAuthAccount(response, request);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/transfer")
    @Operation(summary = "OAuth 새로운 계정 이전")
    public ResponseEntity accountTransfer(@RequestBody OAuthTransferRequest request) {
        oAuthService.accountTransfer(request);

        return ResponseEntity.ok().build();
    }

}
