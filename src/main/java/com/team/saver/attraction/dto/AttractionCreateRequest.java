package com.team.saver.attraction.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AttractionCreateRequest {

    private String title;

    private String introduce;

    private List<String> tags;

}
