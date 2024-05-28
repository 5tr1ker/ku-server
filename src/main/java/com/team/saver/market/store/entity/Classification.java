package com.team.saver.market.store.entity;

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
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long classificationId;

    @Column(nullable = false)
    private String classification;

    public static Classification createEntity(String classification) {
        return Classification.builder()
                .classification(classification)
                .build();
    }

}
