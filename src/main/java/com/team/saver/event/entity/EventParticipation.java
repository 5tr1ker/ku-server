package com.team.saver.event.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventParticipationId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;

}
