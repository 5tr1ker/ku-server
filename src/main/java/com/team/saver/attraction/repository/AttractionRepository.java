package com.team.saver.attraction.repository;

import com.team.saver.attraction.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {

}
