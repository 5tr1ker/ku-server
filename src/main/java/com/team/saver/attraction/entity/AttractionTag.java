package com.team.saver.attraction.entity;

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
public class AttractionTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attractionTagId;

    @Column(nullable = false)
    private String tagContent;

}
