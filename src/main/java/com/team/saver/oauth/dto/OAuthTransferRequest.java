package com.team.saver.oauth.dto;

import com.team.saver.oauth.util.OAuthType;
import lombok.Getter;

@Getter
public class OAuthTransferRequest {

    private String targetEmail;

    private OAuthType type;

    private String accessToken;

}
