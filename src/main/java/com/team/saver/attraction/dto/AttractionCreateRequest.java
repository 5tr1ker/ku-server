package com.team.saver.attraction.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AttractionCreateRequest {

    private String description;

    private List<String> tags;

}
