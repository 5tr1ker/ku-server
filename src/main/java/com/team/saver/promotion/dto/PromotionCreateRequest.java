package com.team.saver.promotion.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PromotionCreateRequest {

    private String introduce;

    private List<String> tags;

}
