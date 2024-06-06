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
        // 30 - 40 - 50 - 60 - 70 - 80 - 90 - 100 ( 80 { 7 } )
        // 20 - 35 - 50 - 65 - 80 - 95 - 110 - 125 ( 50 { 4 } )
        long userLevel = (long) Math.ceil(( totalExp - initExp ) / increaseExp ) + 1;
        long nextLevelExp = initExp + increaseExp * ( userLevel - 1 ); // 50

        return MissionLevelResponse.builder()
                .nextLevelExp(nextLevelExp)
                .userExp(totalExp)
                .userLevel(userLevel)
                .requireToLevelUp(nextLevelExp - totalExp)
                .build();
    }
}
