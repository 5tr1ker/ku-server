package com.team.saver.security.util.inspection.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InspectionTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inspectionTimeId;

    private LocalDateTime inspectionStart;

    private LocalDateTime inspectionEnd;

    public static InspectionTime createEntity(LocalDateTime start, LocalDateTime end) {
        return InspectionTime.builder()
                .inspectionStart(start)
                .inspectionEnd(end)
                .build();
    }

}
