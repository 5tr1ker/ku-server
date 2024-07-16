package com.team.saver.attraction.promotion.entity;

import com.team.saver.attraction.promotion.dto.PromotionCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long promotionId;

    @Setter
    private String imageUrl;

    private String introduce;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PromotionTagRelationShip> promotionTagRelationShips = new ArrayList<>();

    public static Promotion createEntity(PromotionCreateRequest request) {
        return Promotion.builder()
                .introduce(request.getIntroduce())
                .build();
    }

    public void addTag(PromotionTagRelationShip tag) {
        promotionTagRelationShips.add(tag);
        tag.setPromotion(this);
    }

}
