package com.team.saver.promotion.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PromotionTagRelationShip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long promotionTagRelationShipId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    private PromotionTag promotionTag;

    public static PromotionTagRelationShip createEntity(Promotion promotion, PromotionTag promotionTag) {
        return PromotionTagRelationShip.builder()
                .promotion(promotion)
                .promotionTag(promotionTag)
                .build();
    }

}
