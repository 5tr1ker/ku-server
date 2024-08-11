package com.team.saver.report.repository;

import com.team.saver.report.dto.ReportResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomReportRepository {

    List<ReportResponse> findAllReport(Pageable pageable);

}
