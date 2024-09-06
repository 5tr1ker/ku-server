package com.team.saver.account.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum School {

    KONKUK_GLOCAL("kku.ac.kr" , "건국대학교 충주캠퍼스");

    private final String emailDomain;

    @JsonValue
    private final String koreaName;

}
