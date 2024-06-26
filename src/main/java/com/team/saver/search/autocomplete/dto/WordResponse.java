package com.team.saver.search.autocomplete.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WordResponse {

    private String word;

    private int frequency;

}