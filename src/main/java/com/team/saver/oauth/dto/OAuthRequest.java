package com.team.saver.oauth.dto;

import com.team.saver.oauth.util.OAuthType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthRequest {

    private OAuthType type;

    private String accessToken;


    public static OAuthRequest createEntity(OAuthTransferRequest request) {
        return OAuthRequest.builder()
                .type(request.getType())
                .accessToken(request.getAccessToken())
                .build();
    }

}
