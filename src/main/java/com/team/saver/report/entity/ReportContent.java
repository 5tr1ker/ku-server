package com.team.saver.report.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportContent {

    REVIEW("리뷰"),
    PARTNER_REQUEST("파트너쉽 요청");

    @JsonValue
    private final String content;

}
