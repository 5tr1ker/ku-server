package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // fail
    REQUEST_FAIL("-1" , "유효하지 않는 요청입니다."),

    // success
    REQUEST_SUCCESS("1" , "요청이 성공적으로 완료되었습니다.");

    private final String code;
    private final String message;
}
