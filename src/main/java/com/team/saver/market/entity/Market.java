package com.team.saver.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Market {

    @Id @GeneratedValue
    private long marketId;

    @Enumerated(EnumType.STRING)
    MainCategory mainCategory;

    private double locationX;

    private double locationY;

}
