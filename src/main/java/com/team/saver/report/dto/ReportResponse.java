package com.team.saver.report.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.report.entity.ReportContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportResponse {

    private long reportId;

    private String title;

    private ReportContent reportContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    private boolean isRead;

}
