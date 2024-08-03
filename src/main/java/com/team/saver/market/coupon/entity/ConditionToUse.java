package com.team.saver.market.coupon.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConditionToUse {

    MINIMUM_AMOUNT("%s원 이상 구매 시 사용가능"),
    FIRST_PURCHASE("잎사이 첫 구매 시 사용가능");

    @JsonValue
    private final String description;

}
