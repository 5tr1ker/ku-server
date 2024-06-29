package com.team.saver.account.dto;

import com.team.saver.account.entity.Account;
import com.team.saver.oauth.util.OAuthType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AccountResponse {

    private long accountId;

    private String email;

    private String schoolEmail;

    private String phone;

    private String age;

    private String name;

    private String profileImage;

    private OAuthType oAuthType;

    private LocalDate joinDate;

    private LocalDate lastedLoginDate;

    private long loginCount;

    private String role;

    public static AccountResponse of(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .schoolEmail(account.getSchoolEmail())
                .phone(account.getPhone())
                .age(account.getAge())
                .name(account.getName())
                .profileImage(account.getProfileImage())
                .oAuthType(account.getOAuthType())
                .joinDate(account.getJoinDate())
                .lastedLoginDate(account.getLastedLoginDate())
                .loginCount(account.getLoginCount())
                .role(account.getRole().getTitle())
                .build();
    }


}
