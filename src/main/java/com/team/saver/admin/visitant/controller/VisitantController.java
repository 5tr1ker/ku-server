package com.team.saver.admin.visitant.controller;

import com.team.saver.admin.visitant.dto.VisitantResponse;
import com.team.saver.admin.visitant.service.VisitantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VisitantController {

    private final VisitantService visitantService;

    @GetMapping("/v1/admin/visitant/count")
    @Operation(summary = "[ 어드민-전용 ] 최근 14일의 방문자 가져오기 [ 113 ]")
    public ResponseEntity findVisitantCountByVisitDate() {
        List<VisitantResponse> result = visitantService.findVisitantCountByVisitDate();

        return ResponseEntity.ok(result);
    }

}
