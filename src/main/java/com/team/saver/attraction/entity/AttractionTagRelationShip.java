package com.team.saver.attraction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AttractionTagRelationShip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionTagRelationShipId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attraction attraction;

    @ManyToOne(fetch = FetchType.LAZY)
    private AttractionTag attractionTag;

}
