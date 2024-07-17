package com.team.saver.attraction.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class AttractionResponse {

    private long attractionId;

    private String attractionName;

    private String attractionDescription;

    private String imageUrl;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String eventMessage;

    private double locationX;

    private double locationY;

}
