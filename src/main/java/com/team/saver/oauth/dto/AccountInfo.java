package com.team.saver.oauth.dto;

import com.team.saver.account.entity.Account;
import com.team.saver.account.entity.UserRole;
import com.team.saver.account.entity.UserType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountInfo {

    private String name;
    private String age;
    private String phone;
    private String email;

    public Account createAccountEntity() {
        return Account.builder()
                .email(email)
                .role(UserRole.NORMAL)
                .type(UserType.OAUTH)
                .build();
    }

}
