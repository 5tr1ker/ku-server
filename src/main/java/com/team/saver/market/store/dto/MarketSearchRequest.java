package com.team.saver.market.store.dto;

import com.team.saver.market.store.util.SortType;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
public class MarketSearchRequest {

    private SortType sort;

    private double locationX;

    private double locationY;
}
