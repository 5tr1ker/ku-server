package com.team.saver.attraction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attraction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionId;

    private String imageUrl;

    private String description;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AttractionTagRelationShip> attractionTagRelationShips;

}
