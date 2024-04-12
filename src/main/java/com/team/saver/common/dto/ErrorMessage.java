package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUNT_USER("사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    ALREADY_EXISTS_USER("이미 존재하는 사용자입니다."),
    NOT_MATCHED_CODE("인증 코드가 일치하지 않습니다."),
    SMTP_SERVER_ERROR("메일 서버에서 오류가 발생했습니다."),
    UNKNOWN_OAUTH_TYPE("알 수 없는 OAuth Type 입니다."),
    NOT_VALID_GENERAL_USER("해당 계정은 일반 사용자가 아닙니다."),
    NOT_VALID_OAUTH_USER("해당 계정은 OAuth2.0 사용자가 아닙니다.");

    private final String message;

}
