package com.team.saver.oauth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthType {

    KAKAO("kakao" , "카카오"),
    NAVER("naver" , "네이버"),
    GOOGLE("google" , "구글");

    private final String type;
    private final String type_kr;

}
