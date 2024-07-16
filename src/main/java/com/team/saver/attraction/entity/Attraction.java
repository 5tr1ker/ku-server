package com.team.saver.attraction.entity;

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

    @Column(nullable = false)
    private String attractionDescription;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Setter
    private String eventMessage;

    @Column(nullable = false)
    private double locationX;

    @Column(nullable = false)
    private double locationY;


}
