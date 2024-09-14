package com.team.saver.market.store.util;

import com.team.saver.market.store.dto.MarketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Component
public class MarketRecommendContainer {

    private PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());

    public void addMarket(MarketResponse marketResponse, double score) {
        queue.add(new Node(marketResponse, score));
    }

    public List<MarketResponse> getMarket(long count) {
        List<MarketResponse> result = new ArrayList<>();
        List<Node> temp = new ArrayList<>();

        PriorityQueue<Node> copy = new PriorityQueue<>(queue);

        for(int i = 0; i < count && !copy.isEmpty() ; i++) {
            temp.add(copy.peek());
            result.add(copy.poll().marketResponse);
        }

        for(Node node : temp) {
            copy.add(node);
        }

        return result;
    }

    public void clearMarket() {
        queue.clear();
    }

    @AllArgsConstructor
    @Builder
    static private class Node {

        private MarketResponse marketResponse;
        private double score;

    }

    static private class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return Double.compare(o1.score, o2.score);
        }
    }

}
