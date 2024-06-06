package com.team.saver.quest.entity;

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
    private MissionType missionType;

    private long increaseWeight;

    private long initialWeight;

}
