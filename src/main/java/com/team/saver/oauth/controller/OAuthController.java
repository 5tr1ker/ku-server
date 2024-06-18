package com.team.saver.oauth.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.dto.OAuthTransferRequest;
import com.team.saver.oauth.service.OAuthService;
import com.team.saver.security.jwt.dto.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/v1/account/oauth/sign-in")
    @Operation(summary = "OAuth 로그인")
    public ResponseEntity signIn(HttpServletResponse response, @RequestBody OAuthRequest request) {
        Token result = oAuthService.SignInOAuthAccount(response, request);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/v1/account/oauth/transfer")
    @Operation(summary = "[ 로그인 ] OAuth 새로운 계정 이전")
    public ResponseEntity accountTransfer(@RequestBody OAuthTransferRequest request, @Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        oAuthService.accountTransfer(request, currentUser);

        return ResponseEntity.ok().build();
    }

}
