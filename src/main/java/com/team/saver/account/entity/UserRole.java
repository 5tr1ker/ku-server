package com.team.saver.account.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {

    STUDENT("ROLE_STUDENT" , "학생"),
    PARTNER("ROLE_PARTNER" , "파트너"),
    ADMIN("ROLE_ADMIN" , "어드민");

    private final String role;
    private final String title;

}
