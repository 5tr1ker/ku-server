package com.team.saver.oauth.dto;

import com.team.saver.oauth.util.OAuthType;
import lombok.Getter;

@Getter
public class OAuthRequest {

    private OAuthType type;

    private String accessToken;

}
