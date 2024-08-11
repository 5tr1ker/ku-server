package com.team.saver.report.dto;

import com.team.saver.report.entity.ReportContent;
import lombok.Getter;

@Getter
public class ReportCreateRequest {

    private String title;

    private String content;

    private ReportContent reportContent;

}
