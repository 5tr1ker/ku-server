package com.team.saver.event.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventParticipation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventParticipationId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;

    @Setter
    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;

    public static EventParticipation createEntity(Account account, Event event) {
        return EventParticipation.builder()
                .account(account)
                .event(event)
                .build();
    }

}
