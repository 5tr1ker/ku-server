package com.team.saver.quest.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.quest.dto.MissionLevelResponse;
import com.team.saver.quest.dto.MissionResponse;
import com.team.saver.quest.entity.Mission;
import com.team.saver.quest.repository.MissionRepository;
import com.team.saver.quest.util.MissionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MissionUtil missionUtil;

    public List<MissionResponse> getMissionByEmail(CurrentUser currentUser) {
        List<Mission> missionList = missionRepository.findAll();
        List<MissionResponse> result = new ArrayList<>();

        for(Mission mission : missionList) {
            result.add(missionUtil.createMissionResponse(mission, currentUser));
        }

        return result;
    }

    public MissionLevelResponse getMissionLevelByEmail(CurrentUser currentUser) {
        return missionUtil.getMissionLevelByEmail(currentUser);
    }
}
