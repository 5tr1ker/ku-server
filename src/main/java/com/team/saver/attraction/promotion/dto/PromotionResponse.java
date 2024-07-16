package com.team.saver.attraction.promotion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PromotionResponse {

    private long promotionId;

    private String imageUrl;

    private List<String> tags;

    private String introduce;

}
