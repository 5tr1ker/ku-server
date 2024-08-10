package com.team.saver.admin.visitant.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Visitant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitantId;

    @Column(nullable = false)
    private String userIp;

    @Column(nullable = false)
    private String userAgent;

    @Column(nullable = false)
    private LocalDate visitDate;

}
