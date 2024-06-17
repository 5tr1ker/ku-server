package com.team.saver.attraction.repository;

import com.team.saver.attraction.entity.Attraction;
import com.team.saver.attraction.entity.AttractionTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttractionTagRepository extends JpaRepository<AttractionTag, Long> {

    Optional<AttractionTag> findByTagContent(String tagContent);

}
