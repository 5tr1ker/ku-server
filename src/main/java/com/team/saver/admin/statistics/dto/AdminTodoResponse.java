package com.team.saver.admin.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminTodoResponse {

    private long partnerRequestCount;

    private long servicesCount;

    private long reportCount;

}
