package com.team.saver.attraction.promotion.dto;

import com.team.saver.attraction.promotion.entity.PromotionLocation;
import lombok.Getter;

import java.util.List;

@Getter
public class PromotionCreateRequest {

    private String introduce;

    private List<String> tags;

}
