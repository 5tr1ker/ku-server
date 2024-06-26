package com.team.saver.search.popular.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class SearchWordScore {

    private PriorityQueue<com.team.saver.search.popular.dto.SearchWordScore.Node> queue = new PriorityQueue<>(new com.team.saver.search.popular.dto.SearchWordScore.NodeComparator());

    public void clearQueue() {
        queue.clear();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void addSearchWord(long nodeId, String searchWord, double score) {
        queue.add(new com.team.saver.search.popular.dto.SearchWordScore.Node(nodeId, score, searchWord));
    }

    public void initQueue(List<com.team.saver.search.popular.dto.SearchWordScore.Node> searchWordScoreDto) {
        queue = new PriorityQueue<>(new com.team.saver.search.popular.dto.SearchWordScore.NodeComparator());

        for(com.team.saver.search.popular.dto.SearchWordScore.Node node : searchWordScoreDto) {
            queue.add(node);
        }
    }

    public List<com.team.saver.search.popular.dto.SearchWordScore.Node> getAsList(int size) {
        int listSize = Math.min(size, queue.size());

        return new ArrayList<>(queue).subList(0 , listSize);
    }

    public com.team.saver.search.popular.dto.SearchWordScore.Node getSearchWord() {
        return queue.poll();
    }
}
