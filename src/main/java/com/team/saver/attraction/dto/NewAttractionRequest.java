package com.team.saver.attraction.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class NewAttractionRequest {

    private String description;

    private List<String> tags;

}
