package com.team.saver.oauth.service;

import com.team.saver.account.dto.SignUpRequest;
import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserType;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.oauth.dto.AccountInfo;
import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.support.GoogleAttribute;
import com.team.saver.oauth.support.KakaoAttribute;
import com.team.saver.oauth.support.NaverAttribute;
import com.team.saver.oauth.support.OAuthAttribute;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.security.util.SessionManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    private final SessionManager sessionManager;
    private final NaverAttribute naverAttribute;
    private final GoogleAttribute googleAttribute;
    private final KakaoAttribute kakaoAttribute;

    public void SignInOAuthAccount(OAuthRequest request, HttpSession session) {
        Account account = createAccountFromOAuthRequest(request);
        accountRepository.findByEmail(account.getEmail())
                .orElseGet(() -> accountRepository.save(account));

        isValidSignIn(account);

        sessionManager.addSession(account.getEmail(), session);
    }

    private void isValidSignIn(Account account) {
        if(!account.getType().equals(UserType.OAUTH)) {
            throw new RuntimeException(NOT_VALID_OAUTH_USER.getMessage());
        }
    }

    private Account createAccountFromOAuthRequest(OAuthRequest request) {
        OAuthAttribute attribute = findAttribute(request.getType());

        AccountInfo info = getUserInfo(attribute, request.getAccessToken());
        return info.createAccountEntity();
    }

    private OAuthAttribute findAttribute(OAuthType type) {
        if(type.equals(OAuthType.NAVER)) {
            return naverAttribute;
        }
        else if(type.equals(OAuthType.KAKAO)) {
            return kakaoAttribute;
        }
        else if(type.equals(OAuthType.GOOGLE)) {
            return googleAttribute;
        }

        throw new RuntimeException(UNKNOWN_OAUTH_TYPE.getMessage());
    }

    private AccountInfo getUserInfo(OAuthAttribute attribute, String accessToken) {
        ResponseEntity<String> responseResult = attribute.requestUserInfo(accessToken, restTemplate);

        return attribute.getUserInfo(responseResult);
    }

}
