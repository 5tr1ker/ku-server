package com.team.saver.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_USER(-1 , "사용자를 찾을 수 없습니다."),
    NOT_FOUND_ROLE(-2, "유효하지 않는 Role 입니다."),
    NOT_STUDENT_EMAIL(-3, "학생 이메일이 아닙니다."),
    NOT_FOUND_SORT_TYPE(-4, "알 수 없는 탐색 조건입니다."),
    NOT_MATCHED_CODE(-5, "인증 코드가 일치하지 않습니다."),
    SMTP_SERVER_ERROR(-6, "메일 서버에서 오류가 발생했습니다."),
    ALREADY_FAVORITE_MARKET(-7, "이미 관심 목록에 추가된 가게입니다."),
    UNKNOWN_OAUTH_TYPE(-8, "알 수 없는 OAuth Type 입니다."),
    NOT_FOUND_PARTNER_REQUEST(-9, "파트너 요청 페이지를 찾을 수 없습니다."),
    NOT_FOUND_MARKET(-10, "해당 매장을 찾을 수 없습니다."),
    ONLY_DELETE_WRITER(-11, "작성자만 삭제할 수 있습니다."),
    ONLY_UPDATE_WRITER(-12, "작성자만 수정할 수 있습니다."),
    ONLY_ACCESS_OWNER_PARTNER(-13, "파트너 소유자만 접근 가능합니다."),
    EXIST_COUPON(-14, "이미 쿠폰을 다운로드했습니다."),
    NOT_FOUND_COUPON(-15, "쿠폰을 찾을 수 없습니다."),
    TAMPERED_TOKEN(-16, "변조되거나 알 수 없는 토큰입니다."),
    NOT_FOUND_FAVORITE(-17, "관심 목록을 찾을 수 없습니다."),
    EXIST_RECOMMENDER(-18, "이미 추천했습니다."),
    NOT_FOUND_REVIEW(-19, "리뷰를 찾을 수 없습니다."),
    EXIST_CLASSIFICATION(-20, "이미 존재하는 분류입니다."),
    EXIST_SCHOOL_EMAIL(-21, "이미 %s 계정으로 인증된 학교 이메일입니다."),
    INSPECTION_TIME(-22, "점검 시간입니다."),
    EXIST_INSPECTION_TIME(-23, "해당 점검 시간은 중복됩니다."),
    NOT_VALID_INSPECTION_TIME(-24, "유효하지 않는 점검 시간입니다."),
    NOT_INSPECTION_TIME(-25, "현재 점검 시간이 아닙니다."),
    AWS_SERVER_EXCEPTION(-26, "AWS 서버에서 예외가 발생했습니다. : %s"),
    NOT_FOUND_ORDER(-27, "주문 데이터를 찾을 수 없습니다."),
    NOT_FOUND_ORDER_DETAIL(-28, "주문 상세 데이터를 찾을 수 없습니다."),
    NOT_IMAGE_FILE(-29, "이미지 파일 확장자가 아닙니다."),
    TOKEN_EXPIRE(-30, "토큰 유효기간이 경과했습니다."),
    UNKNOWN_EXCEPTION(-31, "알 수 없는 예외가 발생했습니다."),
    BODY_DATA_MISSING(-32, "데이터 일부가 누락되었습니다."),
    IS_EXISTS_REVIEW(-33, "이미 리뷰를 작성했습니다."),
    NOT_FOUND_EVENT(-34, "이벤트 데이터를 찾을 수 없습니다."),
    TOKEN_REISSUE(-35, "새로운 토큰이 재발급 되었습니다."),
    NOT_FOUND_DELIVERY_ADDRESS(-36, "배송지 데이터를 찾을 수 없습니다."),
    CANNOT_DELETE_DEFAULT_DELIVERY_ADDRESS(-37, "기본 배송지는 지울 수 없습니다."),
    NOT_FOUND_ANNOUNCE(-38, "공지사항 데이터를 찾을 수 없습니다."),
    NOT_FOUND_HISTORY(-39, "History 정보를 찾을 수 없습니다."),
    NOT_FOUND_AUTOCOMPLETE(-40, "추천 검색어를 찾을 수 없습니다."),
    NOT_FOUND_PROMOTION(-41, "홍보 데이터를 찾을 수 없습니다."),
    NOT_FOUND_ATTRACTION(-42, "관광 시설을 찾을 수 없습니다."),
    NOT_FOUND_MENU(-43, "메뉴 정보를 찾을 수 없습니다."),
    NOT_FOUND_MENU_OPTION(-44, "메뉴 옵션 정보를 찾을 수 없습니다."),
    NOT_FOUND_BASKET_MENU(-45, "장바구니 등록 상품을 찾을 수 없습니다."),
    NOT_FOUND_CHATROOM(-46, "채팅방 정보를 찾을 수 없습니다."),
    NOT_FOUND_RECOMMENDATION(-47, "추천 데이터를 찾을 수 없습니다."),
    NOT_FOUND_REPORT(-48, "신고 데이터를 찾을 수 없습니다."),
    CANCEL_RECOMMENDER(-49, "추천이 취소되었습니다."),
    ALREADY_PARTICIPANT_EVENT(-50, "이미 참여한 이벤트입니다.");

    private final int errorCode;
    private final String message;

}
