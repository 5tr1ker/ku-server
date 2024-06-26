package com.team.saver.attraction.entity;

import com.team.saver.attraction.dto.AttractionCreateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attraction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionId;

    @Setter
    private String imageUrl;

    private String description;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AttractionTagRelationShip> attractionTagRelationShips = new ArrayList<>();

    public static Attraction createEntity(AttractionCreateRequest request) {
        return Attraction.builder()
                .description(request.getDescription())
                .build();
    }

    public void addTag(AttractionTagRelationShip tag) {
        attractionTagRelationShips.add(tag);
        tag.setAttraction(this);
    }

}
