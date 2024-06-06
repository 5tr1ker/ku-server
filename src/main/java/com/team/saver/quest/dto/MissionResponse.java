package com.team.saver.quest.dto;

import com.team.saver.quest.entity.Mission;
import com.team.saver.quest.util.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionResponse {

    private long level;

    private long increaseWeight;

    private long initialWeight;

    private MissionType missionType;

    private String message;

    public static MissionResponse createEntity(Mission mission, long value) {
        long level = (value - mission.getInitialWeight()) / mission.getInitialWeight() + 1;
        long weight = mission.getIncreaseWeight() + mission.getIncreaseWeight() * level;

        return MissionResponse.builder()
                .increaseWeight(mission.getIncreaseWeight())
                .initialWeight(mission.getInitialWeight())
                .missionType(mission.getMissionType())
                .message(String.format(mission.getMissionType().getMessage(), weight))
                .build();
    }

}
