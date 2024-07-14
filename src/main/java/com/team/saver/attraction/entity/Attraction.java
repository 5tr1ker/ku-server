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

    private String title;

    @Setter
    private String imageUrl;

    private String introduce;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AttractionTagRelationShip> attractionTagRelationShips = new ArrayList<>();

    public static Attraction createEntity(AttractionCreateRequest request) {
        return Attraction.builder()
                .title(request.getTitle())
                .introduce(request.getIntroduce())
                .build();
    }

    public void addTag(AttractionTagRelationShip tag) {
        attractionTagRelationShips.add(tag);
        tag.setAttraction(this);
    }

}
