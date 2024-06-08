package com.team.saver.security.util.inspection.repository;

import com.team.saver.security.util.inspection.entity.InspectionTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InspectionTimeRepository extends JpaRepository<InspectionTime, Long> {

    Optional<InspectionTime> findByInspectionStartLessThanEqualAndInspectionEndGreaterThanEqual(LocalDateTime startTime, LocalDateTime endTime);

}