package com.team.saver.market.store.dto;

import com.team.saver.market.store.entity.MainCategory;
import com.team.saver.market.store.util.SortType;
import lombok.Getter;

@Getter
public class SearchByCategoryRequest {

    private SortType sort;

    private DistanceRequest distance;

    private MainCategory category;

    private String marketName;

}
