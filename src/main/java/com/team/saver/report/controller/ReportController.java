package com.team.saver.report.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.report.dto.ReportCreateRequest;
import com.team.saver.report.dto.ReportResponse;
import com.team.saver.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/v1/reports")
    @Operation(summary = "[ 로그인 ] 컨텐츠 신고 API ( 94 )")
    public ResponseEntity addReport(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                    @RequestBody ReportCreateRequest request) {

        reportService.addReport(currentUser, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/admin/reports")
    @Operation(summary = "[ 어그민-전용 ] 신고 데이터 목록 가져오기 ( 110 )")
    public ResponseEntity findAllReport(Pageable pageable) {
        List<ReportResponse> result = reportService.findAllReport(pageable);

        return ResponseEntity.ok(result);
    }

    @PatchMapping("/v1/admin/reports/{reportId}/read")
    @Operation(summary = "[ 어드민-전용 ] 신고 데이터 읽음 처리 ( 111 )")
    public ResponseEntity updateIsRead(@PathVariable long reportId) {
        reportService.updateIsRead(reportId);

        return ResponseEntity.ok().build();
    }

}
