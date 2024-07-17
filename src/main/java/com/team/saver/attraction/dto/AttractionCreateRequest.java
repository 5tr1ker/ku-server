package com.team.saver.attraction.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class AttractionCreateRequest {

    private String attractionName;

    private String attractionDescription;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String eventMessage;

    private double locationX;

    private double locationY;

}
