package com.team.saver.oauth.support;

import com.team.saver.oauth.dto.AccountInfo;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class GoogleAttribute implements OAuthAttribute {

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String google_user_info_uri;

    @Override
    public ResponseEntity<String> requestUserInfo(String accessToken, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ accessToken);

        URI uri = UriComponentsBuilder
                .fromUriString(google_user_info_uri)
                .build().toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Override
    public AccountInfo getUserInfo(ResponseEntity<String> userInfoRes) {
        JSONObject jsonObject =
                (JSONObject) JSONValue.parse(Objects.requireNonNull(userInfoRes.getBody()));

        return AccountInfo.builder()
                .name((String) jsonObject.get("name"))
                .age((String) jsonObject.get("age"))
                .phone((String) jsonObject.get("mobile"))
                .email((String) jsonObject.get("email"))
                .build();
    }
}
