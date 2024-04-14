package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_USER("사용자를 찾을 수 없습니다."),
    NOT_FOUND_SORT_TYPE("알 수 없는 탐색 조건입니다."),
    NOT_MATCHED_CODE("인증 코드가 일치하지 않습니다."),
    SMTP_SERVER_ERROR("메일 서버에서 오류가 발생했습니다."),
    ALREADY_FAVORITE_MARKET("이미 관심 목록에 추가된 가게입니다."),
    UNKNOWN_OAUTH_TYPE("알 수 없는 OAuth Type 입니다."),
    NOT_FOUND_PARTNER_REQUEST("파트너 요청 페이지를 찾을 수 없습니다."),
    NOT_FOUND_MARKET("해당 매장을 찾을 수 없습니다."),
    ONLY_DELETE_WRITER("작성자만 삭제할 수 있습니다."),
    ONLY_ACCESS_OWNER_PARTNER("파트너 소유자만 접근 가능합니다."),
    EXIST_COUPON("이미 쿠폰을 다운로드했습니다."),
    NOT_FOUND_COUPON("쿠폰을 찾을 수 없습니다."),
    TAMPERED_TOKEN("변조되거나 알 수 없는 토큰입니다."),
    NOT_FOUND_FAVORITE("관심 목록을 찾을 수 없습니다.");

    private final String message;

}
