package com.team.saver.oauth.support;

import com.team.saver.oauth.dto.AccountInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public interface OAuthAttribute {

    ResponseEntity<String> requestUserInfo(String accessToken, RestTemplate restTemplate);

    AccountInfo getUserInfo(ResponseEntity<String> userInfoRes);

}
