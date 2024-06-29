package com.team.saver.account.dto;

import lombok.Getter;

@Getter
public class AccountUpdateRequest {

    private long accountId;

    private String phone;

    private String age;

    private String name;

}
