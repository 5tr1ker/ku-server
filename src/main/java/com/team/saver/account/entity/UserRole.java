package com.team.saver.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    NORMAL("ROLE_NORMAL" , "일반 사용자");

    private final String role;
    private final String title;

}
