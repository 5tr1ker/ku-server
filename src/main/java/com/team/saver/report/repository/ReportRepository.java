package com.team.saver.report.repository;

import com.team.saver.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, CustomReportRepository {

}
