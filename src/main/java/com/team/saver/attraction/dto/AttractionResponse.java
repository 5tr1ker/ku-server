package com.team.saver.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AttractionResponse {

    private long attractionId;

    private String imageUrl;

    private List<String> tag;

    private String description;

}
