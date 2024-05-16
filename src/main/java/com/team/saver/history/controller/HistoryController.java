package com.team.saver.history.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.history.dto.HistoryResponse;
import com.team.saver.history.service.HistoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity findAllByAccount(@LogIn CurrentUser currentUser) {
        List<HistoryResponse> result = historyService.findAllByAccount(currentUser);

        return ResponseEntity.ok(result);
    }

    

}
