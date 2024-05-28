package com.team.saver.market.store.repository;

import com.team.saver.market.store.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    Optional<Classification> findByClassification(String classification);

}
