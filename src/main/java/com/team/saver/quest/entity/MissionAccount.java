package com.team.saver.quest.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MissionAccount {

    @Id
    @GeneratedValue
    private long missionAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Mission mission;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

}
