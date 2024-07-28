package com.team.saver.announce.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AnnounceType {

    EVENT("이벤트"),
    ANNOUNCE("안내");

    @JsonValue
    private final String description;

}
