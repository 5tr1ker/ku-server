package com.team.saver.security.controller;

import com.team.saver.common.dto.ResponseMessage;
import com.team.saver.security.util.inspection.dto.InspectionTimeRequest;
import com.team.saver.security.util.inspection.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @GetMapping("/v1/security/is-connect")
    @Operation(summary = "서버와 연결 여부 확인 API [ 서버 점검 여부 ]")
    public ResponseEntity checkConnection() {
        ResponseMessage result = securityService.isInspectionTime();

        if(result == null) {

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/v1/security/inspection")
    @Operation(summary = "새로운 점검 시간 설정")
    public ResponseEntity setInspection(@RequestBody InspectionTimeRequest request) {
        securityService.setInspectionTime(request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/security/inspection")
    @Operation(summary = "현재 점검 시간 제거")
    public ResponseEntity deleteInspection() {
        securityService.deleteInspection();

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/v1/security/inspection")
    @Operation(summary = "현재 점검 중인 시간 변경")
    public ResponseEntity updateInspection(@RequestBody InspectionTimeRequest request) {
        securityService.updateInspection(request);

        return ResponseEntity.ok().build();
    }

}
