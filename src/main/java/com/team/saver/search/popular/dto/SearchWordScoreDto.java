package com.team.saver.search.popular.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;

@Getter
@Builder
@AllArgsConstructor
public class SearchWordScoreDto implements Comparator<SearchWordScoreDto> {
    private double score;
    private String searchWord;
    private int rankChangeValue;

    public SearchWordScoreDto(double score, String searchWord) {
        this.score = score;
        this.searchWord = searchWord;
    }

    public SearchWordScoreDto(SearchWordScoreDto searchWordScoreDto, int rankChangeValue) {
        this.score = searchWordScoreDto.getScore();
        this.searchWord = searchWordScoreDto.getSearchWord();
        this.rankChangeValue = rankChangeValue;
    }

    @Override
    public int compare(SearchWordScoreDto o1, SearchWordScoreDto o2) {
        return Double.compare(o1.score, o2.score);
    }
}
