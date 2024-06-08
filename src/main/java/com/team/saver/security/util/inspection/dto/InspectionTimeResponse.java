package com.team.saver.security.util.inspection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class InspectionTimeResponse {

    private LocalTime start;

    private LocalTime end;

    @Override
    public String toString() {
        String startTime = start.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String endTime = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format("%s ~ %s" , startTime, endTime);
    }

}
