package com.team.saver.report.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.report.dto.ReportCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account reporter;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportContent reportContent;

    @Column(nullable = false)
    @Builder.Default
    private boolean isRead = false;

    public static Report createEntity(Account account, ReportCreateRequest request) {
        return Report.builder()
                .reporter(account)
                .title(request.getTitle())
                .reportContent(request.getReportContent())
                .content(request.getContent())
                .build();
    }

    public void readReport() {
        this.isRead = true;
    }
}
