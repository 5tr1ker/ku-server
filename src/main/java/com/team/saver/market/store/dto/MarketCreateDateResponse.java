package com.team.saver.market.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MarketCreateDateResponse {

    private long marketId;

    private String marketName;

    private String marketImage;

    private LocalDate createDate;

}
