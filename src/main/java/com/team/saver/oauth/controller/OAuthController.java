package com.team.saver.oauth.controller;

import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.service.OAuthService;
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
    public ResponseEntity signIn(@RequestBody OAuthRequest request, HttpSession session) {
        oAuthService.SignInOAuthAccount(request, session);

        return ResponseEntity.ok().build();
    }

}
