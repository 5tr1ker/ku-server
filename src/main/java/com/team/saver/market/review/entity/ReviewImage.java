package com.team.saver.market.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewImageId;

    private String imageUrl;

    @Builder.Default
    private boolean isDelete = false;

    public static ReviewImage createEntity(String imageUrl) {
        return ReviewImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

}
