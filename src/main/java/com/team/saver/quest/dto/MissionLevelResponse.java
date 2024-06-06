package com.team.saver.quest.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MissionLevelResponse {

    private long nextLevelExp;

    private long requireToLevelUp;

    private long userExp;

    private long userLevel;

    public static MissionLevelResponse createEntity(long initExp, long increaseExp, long totalExp) {
        long userLevel = (long) Math.ceil(( totalExp - ( double ) initExp ) / increaseExp ) + 1;
        if(totalExp - initExp % increaseExp == 0) {
            userLevel += 1;
        }

        long nextLevelExp = initExp + increaseExp * ( userLevel - 1 );

        return MissionLevelResponse.builder()
                .nextLevelExp(nextLevelExp)
                .userExp(totalExp)
                .userLevel(userLevel)
                .requireToLevelUp(nextLevelExp - totalExp)
                .build();
    }
}
