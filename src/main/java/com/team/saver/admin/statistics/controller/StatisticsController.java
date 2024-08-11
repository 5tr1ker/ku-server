package com.team.saver.admin.statistics.controller;

import com.team.saver.admin.statistics.dto.AdminTodoResponse;
import com.team.saver.admin.statistics.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/v1/admin/statistics")
    @Operation(summary = "[ 어드민-전용 ] 어드민 Todo Count 가져오기 (114)")
    public ResponseEntity findAdminTodo() {
        AdminTodoResponse adminTodoResponse = statisticsService.findAdminTodo();

        return ResponseEntity.ok(adminTodoResponse);
    }

}
