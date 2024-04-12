package com.team.saver.oauth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountInfo {

    private String name;
    private String age;
    private String phone;
    private String email;

}
