package com.team.saver.announce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announce {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long announceId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime writeTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private AnnounceType announceType;

    @Builder.Default
    @Column(nullable = false)
    private boolean isFix = false;

}
