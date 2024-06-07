package com.team.saver.history.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.history.dto.HistoryDeleteRequest;
import com.team.saver.history.dto.HistoryRequest;
import com.team.saver.history.dto.HistoryResponse;
import com.team.saver.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "[ 로그인 ] History 정보 가져오기")
    public ResponseEntity findAllByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<HistoryResponse> result = historyService.findAllByAccount(currentUser);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "[ 로그인 ] History 데이터 추가")
    public ResponseEntity addHistoryByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                              @RequestBody HistoryRequest historyRequest) {
        historyService.addHistoryByAccount(currentUser, historyRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/all")
    @Operation(summary = "[ 로그인 ] 모든 History 제거")
    public ResponseEntity deleteAllHistoryByAccount(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        historyService.deleteAllHistoryByAccount(currentUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    @Operation(summary = "[ 로그인 ] 특정 History 제거")
    public ResponseEntity deleteHistoryByAccountAndHistoryId(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                             @RequestBody HistoryDeleteRequest historyRequest) {
        historyService.deleteHistoryByAccountAndHistoryId(currentUser, historyRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
