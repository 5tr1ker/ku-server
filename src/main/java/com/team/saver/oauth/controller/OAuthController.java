package com.team.saver.oauth.controller;

import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.service.OAuthService;
import com.team.saver.security.jwt.dto.Token;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/sign-in")
    @Operation(summary = "OAuth 로그인")
    public ResponseEntity signIn(@RequestBody OAuthRequest request) {
        Token result = oAuthService.SignInOAuthAccount(request);

        return ResponseEntity.ok(result);
    }

}
