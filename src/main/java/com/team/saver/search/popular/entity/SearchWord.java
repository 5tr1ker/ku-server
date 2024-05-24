package com.team.saver.search.popular.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    @Builder.Default
    @Setter
    private int previousRanking = 0;

    public void updateSearch() {
        recentlySearch += 1;
        daySearch += 1;
        weekSearch += 1;
        totalSearch += 1;
    }

    public static SearchWord createEntity(String searchWord) {
        return SearchWord.builder()
                .searchWord(searchWord)
                .build();
    }


}
