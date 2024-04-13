package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUNT_USER("사용자를 찾을 수 없습니다."),
    NOT_FOUND_SORT_TYPE("알 수 없는 탐색 조건입니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    ALREADY_EXISTS_USER("이미 존재하는 사용자입니다."),
    NOT_MATCHED_CODE("인증 코드가 일치하지 않습니다."),
    SMTP_SERVER_ERROR("메일 서버에서 오류가 발생했습니다."),
    UNKNOWN_OAUTH_TYPE("알 수 없는 OAuth Type 입니다."),
    NOT_FOUNT_PARTNER_REQUEST("파트너 요청 페이지를 찾을 수 없습니다."),
    NOT_FOUND_MARKET("해당 매장을 찾을 수 없습니다."),
    ONLY_DELETE_WRITER("작성자만 삭제할 수 있습니다.");

    private final String message;

}
