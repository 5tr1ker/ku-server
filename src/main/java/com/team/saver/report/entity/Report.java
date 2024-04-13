package com.team.saver.report.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account reporter;

    private String content;

    public static Report createEntity(Account account, String content) {
        return Report.builder()
                .reporter(account)
                .content(content)
                .build();
    }

}
