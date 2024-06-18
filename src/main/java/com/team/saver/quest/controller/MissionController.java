package com.team.saver.quest.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.quest.dto.MissionLevelResponse;
import com.team.saver.quest.dto.MissionResponse;
import com.team.saver.quest.service.MissionService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/v1/missions")
    @Operation(summary = "[ 로그인 ] 미션 데이터 목록 가져오기")
    public ResponseEntity getMissionByEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        List<MissionResponse> result = missionService.getMissionByEmail(currentUser);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/missions/level")
    @Operation(summary = "[ 로그인 ] 미션 수행으로 인한 레벨가져오기")
    public ResponseEntity getMissionLevelByEmail(@Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        MissionLevelResponse result = missionService.getMissionLevelByEmail(currentUser);

        return ResponseEntity.ok(result);
    }

}
