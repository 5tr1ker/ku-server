package com.team.saver.market.store.dto;

import com.team.saver.market.store.util.SortType;
import lombok.Getter;

@Getter
public class SearchMarketRequest {
    private SortType sort;

    private DistanceRequest distance;
}
