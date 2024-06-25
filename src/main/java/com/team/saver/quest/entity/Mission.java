package com.team.saver.quest.entity;

import com.team.saver.quest.dto.MissionCreateRequest;
import com.team.saver.quest.util.MissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long missionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionType missionType;

    @Column(nullable = false)
    private long increaseWeight;

    @Column(nullable = false)
    private long initWeight;

    @Column(nullable = false)
    private long initExp;

    @Column(nullable = false)
    private long increaseExp;

    public static Mission createEntity(MissionCreateRequest request) {
        return Mission.builder()
                .increaseExp(request.getIncreaseExp())
                .initExp(request.getInitExp())
                .increaseWeight(request.getIncreaseWeight())
                .initWeight(request.getInitWeight())
                .build();
    }

}
