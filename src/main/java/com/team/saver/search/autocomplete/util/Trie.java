package com.team.saver.search.autocomplete.util;

import com.team.saver.search.autocomplete.dto.UtilInitDto;
import com.team.saver.search.autocomplete.entity.AutoComplete;
import com.team.saver.search.autocomplete.repository.AutoCompleteRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ToString
class Node {
    Map<String, Node> childedNode = new HashMap<>();
    String word;
    boolean isContainWord;
    int frequency;

    public Node(String word) {
        this.word = word;
        this.frequency = 0;
    }

    public Node(AutoComplete autoComplete) {
        word = autoComplete.getWord();
        frequency = autoComplete.getFrequency();
    }
}

@Component
@RequiredArgsConstructor
public class Trie {

    Node rootNode = new Node("Empty");
    private final AutoCompleteRepository autoCompleteRepository;

    public void initTrie() {
        List<AutoComplete> result = autoCompleteRepository.findAll();

        for(AutoComplete autoComplete : result) {
            Node node = this.rootNode;
            String str = autoComplete.getWord();
            String [] completeWord = new String[str.length()];

            for(int i = 0; i < completeWord.length; i++) {
                String targetString = str.substring(i , i + 1);
                completeWord[i] = targetString;

                if(isHangul(targetString)) {
                    node = addTrieNode_Hangul(targetString, node, completeWord, i);
                } else {
                    node = addTrieNode_English(targetString, node, completeWord);
                }
            }

            node.isContainWord = true;
            node.frequency = autoComplete.getFrequency();
        }
    }

    @Transactional
    public Node insert(String str) {
        Node node = this.rootNode;
        Node currentNode;
        String [] completeWord = new String[str.length()];

        for(int i = 0; i < str.length(); i++) {
            String targetString = str.substring(i , i + 1);
            completeWord[i] = targetString;

            if(isHangul(targetString)) {
                node = addTrieNode_Hangul(targetString, node, completeWord, i);
            } else {
                node = addTrieNode_English(targetString, node, completeWord);
            }
        }

        currentNode = createOrLoadAutoComplete(node.word);

        node.isContainWord = true;
        node.frequency = currentNode.frequency;

        return node;
    }

    public static boolean isHangul(String str) {
        return str.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }


    public static String convertArrayToString(String [] arr) {
        return String.join("" , arr).replaceAll("null","");
    }

    public Node addTrieNode_Hangul(String word, Node node, String [] totalWord, int index) {
        List<Map<String, Integer>> separatedWord = GraphemeSeparation.separation(word);

        for(int i = 0; i < separatedWord.size(); i++) {
            List<String> resultWord = GraphemeSeparation.absorption(separatedWord.get(i));

            for(int j = 0; j < resultWord.size(); j++) {
                String targetWord = resultWord.get(j);
                totalWord[index] = targetWord;

                node = node.childedNode.computeIfAbsent(targetWord, key -> new Node(convertArrayToString(totalWord)));
            }
        }

        return node;
    }

    public Node addTrieNode_English(String str, Node node, String[] totalWord) {
        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.computeIfAbsent(str.substring(i , i + 1).toLowerCase(), key -> new Node(convertArrayToString(totalWord)));
        }

        return node;
    }

    protected Node createOrLoadAutoComplete(String word) {
        AutoComplete autoComplete = autoCompleteRepository.findAutoCompleteByWord(word)
                .orElseGet(() -> autoCompleteRepository.save(AutoComplete.createAutoComplete(word)));

        autoComplete.increaseFrequency(1);

        return new Node(autoComplete);
    }

    public List<UtilInitDto> searchComplete(String searchWord) {
        List<UtilInitDto> result = new ArrayList<>();
        Node node = this.rootNode;

        for(int i = 0; i < searchWord.length(); i++) {
            String targetWord = searchWord.substring(i , i + 1);

            if(isHangul(targetWord)) {
                node = searchSearchWord_Hangul(targetWord, node);
            } else {
                node = searchSearchWord_English(targetWord, node);
            }

            if(node == null) {
                return result;
            }
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while(!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if(currentNode.isContainWord) {
                result.add(new UtilInitDto(currentNode.word, currentNode.frequency));
            }

            for(String str : currentNode.childedNode.keySet()) {
                queue.offer(currentNode.childedNode.get(str));
            }
        }

        Collections.sort(result, new Comparator<UtilInitDto>() {
            @Override
            public int compare(UtilInitDto o1, UtilInitDto o2) {
                return o2.getFrequency() - o1.getFrequency();
            }
        });

        if(result.size() > 10) {
            result.subList(10, result.size()).clear();
        }

        return result;
    }

    private Node searchSearchWord_English(String str, Node node) {

        for(int i = 0; i < str.length(); i++) {
            node = node.childedNode.getOrDefault(str.substring(i , i + 1).toLowerCase(), null );

            if(node == null) {
                return null;
            }
        }

        return node;
    }

    private Node searchSearchWord_Hangul(String str, Node node) {
        List<Map<String, Integer>> separatedWord = GraphemeSeparation.separation(str);

        if(separatedWord.isEmpty()) {
            return node.childedNode.getOrDefault(str, null);
        }

        for(int i = 0; i < separatedWord.size(); i++) {
            List<String> resultWord = GraphemeSeparation.absorption(separatedWord.get(i));

            for(int j = 0; j < resultWord.size(); j++) {
                node = node.childedNode.getOrDefault(resultWord.get(j), null);

                if(node == null) {
                    return null;
                }
            }
        }

        return node;
    }

}