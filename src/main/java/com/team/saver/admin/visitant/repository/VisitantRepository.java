package com.team.saver.admin.visitant.repository;

import com.team.saver.admin.visitant.domain.Visitant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface VisitantRepository extends JpaRepository<Visitant, Long>, CustomVisitantRepository {

    boolean existsByUserIpAndVisitDate(String userIp, LocalDate visitDate);

}
