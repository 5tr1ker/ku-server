package com.team.saver.search.popular.util;

import com.team.saver.search.popular.dto.SearchWordScoreDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class SearchWordScore {

    private PriorityQueue<SearchWordScoreDto> queue = new PriorityQueue<>();

    public void clearQueue() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addSearchWord(String searchWord, double score) {
        queue.add(new SearchWordScoreDto(score, searchWord));
    }

    public void initQueue(List<SearchWordScoreDto> searchWordScoreDto) {
        queue = new PriorityQueue<>(searchWordScoreDto);
    }

    public List<SearchWordScoreDto> getAsList() {
        return new ArrayList<>(queue);
    }

    public SearchWordScoreDto getSearchWord() {
        return queue.poll();
    }
}
