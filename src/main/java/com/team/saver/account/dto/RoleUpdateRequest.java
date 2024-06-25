package com.team.saver.account.dto;

import com.team.saver.account.entity.UserRole;
import lombok.Getter;

@Getter
public class RoleUpdateRequest {

    private UserRole userRole;

    private String schoolEmail;

}
