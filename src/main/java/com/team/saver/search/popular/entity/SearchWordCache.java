package com.team.saver.search.popular.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchWordCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long popularSearchWordId;

    private String userIp;

    private String userAgent;

    private String searchWord;

    private LocalDateTime searchTime;

}
