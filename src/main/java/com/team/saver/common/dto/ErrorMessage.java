package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUNT_USER("사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.");

    private final String message;

}
