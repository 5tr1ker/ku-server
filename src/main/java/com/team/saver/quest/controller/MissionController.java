package com.team.saver.quest.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.quest.dto.MissionResponse;
import com.team.saver.quest.service.MissionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mission")
public class MissionController {

    private final MissionService missionService;

    @GetMapping
    @Operation(summary = "미션 데이터 목록 가져오기")
    public ResponseEntity getMissionByEmail(@LogIn CurrentUser currentUser) {
        List<MissionResponse> result = missionService.getMissionByEmail(currentUser);

        return ResponseEntity.ok(result);
    }

}
