package com.team.saver.search.popular.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

public class SearchWordScoreDto {

    @Getter
    @AllArgsConstructor
    @Builder
    static public class Node {

        private long nodeId;
        private int rank;
        private double score;
        private String searchWord;
        private int rankChangeValue;

        public Node(long nodeId, double score, String searchWord) {
            this.nodeId = nodeId;
            this.score = score;
            this.searchWord = searchWord;
        }

        public Node(long nodeId, int rank, Node searchWordDto, int rankChangeValue) {
            this.nodeId = nodeId;
            this.rank = rank;
            this.score = searchWordDto.score;
            this.searchWord = searchWordDto.searchWord;
            this.rankChangeValue = rankChangeValue;
        }
    }

    static public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if(o1.score == o2.score) {
                return Double.compare(o1.nodeId, o2.nodeId);
            }

            return Double.compare(o2.score, o1.score);
        }
    }

}
