package com.team.saver.market.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MarketClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long marketClassificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classification classification;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private Market market;

    public static MarketClassification createEntity(Classification classification, Market market) {
        return MarketClassification.builder()
                .market(market)
                .classification(classification)
                .build();
    }

}
