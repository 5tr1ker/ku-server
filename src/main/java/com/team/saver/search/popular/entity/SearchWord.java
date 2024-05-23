package com.team.saver.search.popular.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long searchWordId;

    @Column(nullable = false)
    private String searchWord;

    @Column(nullable = false)
    @Builder.Default
    private int recentlySearch = 0;

    @Column(nullable = false)
    @Builder.Default
    private int daySearch = 0;

    @Column(nullable = false)
    @Builder.Default
    private int weekSearch = 0;

    @Column(nullable = false)
    @Builder.Default
    private int totalSearch = 0;

    public void updateSearch() {
        recentlySearch += 1;
        daySearch += 1;
        weekSearch += 1;
        totalSearch += 1;
    }

    public void resetRecentlySearch() {
        recentlySearch = 0;
    }

    public void resetDaySearch() {
        daySearch = 0;
    }

    public void resetWeekSearch() {
        weekSearch = 0;
    }

    public static SearchWord createEntity(String searchWord) {
        return SearchWord.builder()
                .searchWord(searchWord)
                .build();
    }


}
