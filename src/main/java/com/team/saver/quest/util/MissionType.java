package com.team.saver.quest.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionType {

    ATTENDANCE("attendance" , "잎사이 %d일 출석하기"),
    USE_DISCOUNT_COUPON("useDiscountCoupon" , "잎사이 할인쿠폰 %d회 사용하기"),
    WRITE_REVIEW("writeReview" , "리뷰 %d번 쓰기");

    private final String type;

    private final String message;

}
