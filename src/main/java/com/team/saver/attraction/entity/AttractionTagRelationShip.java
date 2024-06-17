package com.team.saver.attraction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AttractionTagRelationShip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionTagRelationShipId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Attraction attraction;

    @ManyToOne(fetch = FetchType.LAZY)
    private AttractionTag attractionTag;

    public static AttractionTagRelationShip createEntity(Attraction attraction, AttractionTag attractionTag) {
        return AttractionTagRelationShip.builder()
                .attraction(attraction)
                .attractionTag(attractionTag)
                .build();
    }

}
