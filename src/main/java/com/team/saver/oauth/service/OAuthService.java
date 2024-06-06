package com.team.saver.oauth.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.oauth.dto.AccountInfo;
import com.team.saver.oauth.dto.OAuthRequest;
import com.team.saver.oauth.support.GoogleAttribute;
import com.team.saver.oauth.support.KakaoAttribute;
import com.team.saver.oauth.support.NaverAttribute;
import com.team.saver.oauth.support.OAuthAttribute;
import com.team.saver.oauth.util.OAuthType;
import com.team.saver.security.jwt.dto.Token;
import com.team.saver.security.jwt.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static com.team.saver.common.dto.ErrorMessage.UNKNOWN_OAUTH_TYPE;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate;
    private final NaverAttribute naverAttribute;
    private final GoogleAttribute googleAttribute;
    private final KakaoAttribute kakaoAttribute;

    @Transactional
    public Token SignInOAuthAccount(HttpServletResponse response, OAuthRequest request) {
        Account account = createAccountFromOAuthRequest(request);
        accountRepository.findByEmail(account.getEmail())
                .orElseGet(() -> accountRepository.save(account));

        updateLoginCount(account);

        return jwtTokenProvider.login(response, account.getEmail(), account.getRole());
    }

    @Transactional
    public void updateLoginCount(Account account) {
        if(account.getLastedLoginDate().compareTo(LocalDate.now()) < 0) {
            account.updateLastedLoginDate();
        }
    }

    private Account createAccountFromOAuthRequest(OAuthRequest request) {
        OAuthAttribute attribute = findAttribute(request.getType());

        AccountInfo info = getUserInfo(attribute, request.getAccessToken());
        return Account.createEntity(info, request.getType());
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

        throw new CustomRuntimeException(UNKNOWN_OAUTH_TYPE);
    }

    private AccountInfo getUserInfo(OAuthAttribute attribute, String accessToken) {
        ResponseEntity<String> responseResult = attribute.requestUserInfo(accessToken, restTemplate);

        return attribute.getUserInfo(responseResult);
    }

}
