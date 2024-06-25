package com.team.saver.event.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class EventCreateRequest {

    private String title;

    private String description;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;


}
