package com.team.saver.oauth.support;

import com.team.saver.oauth.dto.AccountInfo;
import lombok.Getter;
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
public class NaverAttribute implements OAuthAttribute {

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naver_user_info_uri;

    @Override
    public ResponseEntity<String> requestUserInfo(String accessToken, RestTemplate restTemplate) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + accessToken);

        URI uri = UriComponentsBuilder
                .fromUriString(naver_user_info_uri)
                .build().toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);
    }

    @Override
    public AccountInfo getUserInfo(ResponseEntity<String> userInfoRes) {
        JSONObject jsonObject =
                (JSONObject) JSONValue.parse(Objects.requireNonNull(userInfoRes.getBody()));
        JSONObject accountObject = (JSONObject) jsonObject.get("response");

        return AccountInfo.builder()
                .name((String) accountObject.get("name"))
                .age((String) accountObject.get("age"))
                .phone((String) accountObject.get("mobile"))
                .email((String) accountObject.get("email"))
                .build();
    }
}
