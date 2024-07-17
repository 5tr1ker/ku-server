package com.team.saver.attraction.dto;

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

    @Setter
    private double distance;

    public AttractionResponse(long attractionId, String attractionName, String attractionDescription, String imageUrl, LocalTime openTime, LocalTime closeTime, String eventMessage, double locationX, double locationY) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.attractionDescription = attractionDescription;
        this.imageUrl = imageUrl;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.eventMessage = eventMessage;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
