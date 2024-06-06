package com.team.saver.account.dto;

import com.team.saver.account.entity.UserRole;
import lombok.Getter;

@Getter
public class UpdateRoleRequest {

    private UserRole userRole;

    private String schoolEmail;

}
