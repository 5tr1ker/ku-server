package com.team.saver.report.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.report.dto.ReportCreateRequest;
import com.team.saver.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/v1/reports")
    @Operation(summary = "[ 로그인 ] 컨텐츠 신고 API")
    public ResponseEntity addReport(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                    @RequestBody ReportCreateRequest request) {

        reportService.addReport(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
