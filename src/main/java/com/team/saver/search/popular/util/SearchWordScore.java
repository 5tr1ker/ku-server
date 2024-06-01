package com.team.saver.search.popular.util;

import com.team.saver.search.popular.dto.SearchWordScoreDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class SearchWordScore {

    private PriorityQueue<SearchWordScoreDto.Node> queue = new PriorityQueue<>(new SearchWordScoreDto.NodeComparator());

    public void clearQueue() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addSearchWord(long nodeId, String searchWord, double score) {
        queue.add(new SearchWordScoreDto.Node(nodeId, score, searchWord));
    }

    public void initQueue(List<SearchWordScoreDto.Node> searchWordScoreDto) {
        queue = new PriorityQueue<>(new SearchWordScoreDto.NodeComparator());

        for(SearchWordScoreDto.Node node : searchWordScoreDto) {
            queue.add(node);
        }
    }

    public List<SearchWordScoreDto.Node> getAsList(int size) {
        int listSize = Math.min(size, queue.size());

        return new ArrayList<>(queue).subList(0 , listSize);
    }

    public SearchWordScoreDto.Node getSearchWord() {
        return queue.poll();
    }
}
