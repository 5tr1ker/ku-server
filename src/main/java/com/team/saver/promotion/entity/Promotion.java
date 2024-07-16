package com.team.saver.promotion.entity;

import com.team.saver.promotion.dto.PromotionCreateRequest;
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

    @Enumerated(EnumType.STRING)
    private PromotionLocation promotionLocation;

    private String introduce;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PromotionTagRelationShip> promotionTagRelationShips = new ArrayList<>();

    public static Promotion createEntity(PromotionCreateRequest request, PromotionLocation location) {
        return Promotion.builder()
                .promotionLocation(location)
                .introduce(request.getIntroduce())
                .build();
    }

    public void addTag(PromotionTagRelationShip tag) {
        promotionTagRelationShips.add(tag);
        tag.setPromotion(this);
    }

}
