package com.team.saver.attraction.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AttractionResponse {

    private long attractionId;

    private String imageUrl;

    private List<String> tag;

    private String description;

}
