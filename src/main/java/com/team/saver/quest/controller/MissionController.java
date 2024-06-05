package com.team.saver.quest.controller;

import com.team.saver.quest.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;

}
