package com.team.saver.quest.dto;

import com.team.saver.quest.util.MissionType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionRequest {

    private MissionType missionType;

    private long increaseWeight;

    private long initWeight;

    private long initExp;

    private long increaseExp;

}
