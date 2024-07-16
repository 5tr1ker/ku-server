package com.team.saver.attraction.promotion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PromotionTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long promotionTagId;

    @Column(nullable = false)
    private String tagContent;

    public static PromotionTag createEntity(String tagContent) {
        return PromotionTag.builder()
                .tagContent(tagContent)
                .build();
    }

}
