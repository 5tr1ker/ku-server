package com.team.saver.market.review.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewImageId;

    private String imageUrl;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Review review;

    @Builder.Default
    private boolean isDelete = false;

    public static ReviewImage createEntity(String imageUrl) {
        return ReviewImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

}
