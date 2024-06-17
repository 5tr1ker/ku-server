package com.team.saver.attraction.entity;

import com.team.saver.attraction.dto.NewAttractionRequest;
import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.Attr;

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

    public static Attraction createEntity(NewAttractionRequest request) {
        return Attraction.builder()
                .description(request.getDescription())
                .build();
    }

    public void addTag(AttractionTagRelationShip tag) {
        attractionTagRelationShips.add(tag);
        tag.setAttraction(this);
    }

}
