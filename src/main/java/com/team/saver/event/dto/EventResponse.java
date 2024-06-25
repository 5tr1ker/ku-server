package com.team.saver.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EventResponse {

    private long eventId;

    private String title;

    private String imageUrl;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;


}
