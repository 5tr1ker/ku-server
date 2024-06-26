package com.team.saver.history.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.history.dto.HistoryCreateRequest;
import com.team.saver.history.dto.HistoryResponse;
import com.team.saver.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/v1/histories")
    @Operation(summary = "[ 로그인 ] History 정보 가져오기")
    public ResponseEntity findAllByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<HistoryResponse> result = historyService.findAllByAccount(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/histories")
    @Operation(summary = "[ 로그인 ] History 데이터 추가")
    public ResponseEntity addHistoryByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                              @RequestBody HistoryCreateRequest historyCreateRequest) {
        historyService.addHistoryByAccount(currentUser, historyCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/histories/all")
    @Operation(summary = "[ 로그인 ] 모든 History 제거")
    public ResponseEntity deleteAllHistoryByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        historyService.deleteAllHistoryByAccount(currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/v1/histories/{historyId}")
    @Operation(summary = "[ 로그인 ] 특정 History 제거")
    public ResponseEntity deleteHistoryByAccountAndHistoryId(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                             @PathVariable long historyId) {
        historyService.deleteHistoryByAccountAndHistoryId(currentUser, historyId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
