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

    private long initWeight;

    private long initExp;

    private long increaseExp;

    private long expValue;

    private long gainExp_previousLevel;

    private long gainExp_currentLevel;

    private long gainExp_nextLevel;

    private long weight_previousLevel;

    private long weight_currentLevel;

    private long weight_nextLevel;

    private MissionType missionType;

    private String message;

    public static MissionResponse createEntity(Mission mission, long value) {
        long level = ( long ) Math.ceil(( value ) / ( double ) mission.getInitWeight());
        if(value % mission.getInitWeight() == 0) {
            level += 1;
        }

        long weight = mission.getInitWeight() + mission.getIncreaseWeight() * ( level - 1 );
        long gainExp_currentLevel = mission.getInitExp() + mission.getIncreaseExp() * (level - 1);

        return MissionResponse.builder()
                .level(level)
                .increaseWeight(mission.getIncreaseWeight())
                .initWeight(mission.getInitWeight())
                .initExp(mission.getInitExp())
                .increaseExp(mission.getIncreaseExp())
                .missionType(mission.getMissionType())
                .expValue(value)
                .gainExp_previousLevel(gainExp_currentLevel - mission.getIncreaseExp())
                .gainExp_currentLevel(gainExp_currentLevel)
                .gainExp_nextLevel(gainExp_currentLevel + mission.getIncreaseExp())
                .weight_previousLevel(weight - mission.getIncreaseWeight())
                .weight_currentLevel(weight)
                .weight_nextLevel(weight + mission.getIncreaseWeight())
                .message(String.format(mission.getMissionType().getMessage(), weight))
                .build();
    }

}
