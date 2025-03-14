package com.team.saver.history.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long historyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public static History createEntity(Account account, String content) {
        return History.builder()
                .content(content)
                .account(account)
                .localDateTime(LocalDateTime.now())
                .build();
    }

    public void updateTime() {
        localDateTime = LocalDateTime.now();
    }

}
