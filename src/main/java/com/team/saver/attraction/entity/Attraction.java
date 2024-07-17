package com.team.saver.attraction.entity;

import com.team.saver.attraction.dto.AttractionCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Attraction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionId;

    private String attractionName;

    private String attractionDescription;

    @Setter
    private String imageUrl;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Setter
    private String eventMessage;

    @Column(nullable = false)
    private double locationX;

    @Column(nullable = false)
    private double locationY;

    public static Attraction createEntity(AttractionCreateRequest request) {
        return Attraction.builder()
                .attractionName(request.getAttractionName())
                .attractionDescription(request.getAttractionDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .eventMessage(request.getEventMessage())
                .locationX(request.getLocationX())
                .locationY(request.getLocationY())
                .build();
    }

}