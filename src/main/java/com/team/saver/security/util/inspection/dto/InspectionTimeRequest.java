package com.team.saver.security.util.inspection.dto;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class InspectionTimeRequest {

    private LocalTime start;

    private LocalTime end;

}
