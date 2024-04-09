package com.team.saver.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    NORMAL("ROLE_NORMAL" , "일반 사용자"),
    ADMIN("ROLE_ADMIN" , "어드민");

    private final String role;
    private final String title;

}
