package com.team.saver.search.autocomplete.service;

import com.team.saver.search.autocomplete.dto.WordAddRequest;
import com.team.saver.search.autocomplete.dto.WordResponse;
import com.team.saver.search.autocomplete.util.Trie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoCompleteService {

    private final Trie trie;

    @Transactional
    public void addSearchWord(WordAddRequest request) {
        trie.insert(request.getWord());
    }

    @Transactional
    public void addSearchWord(String searchWord) {
        trie.insert(searchWord);
    }

    public List<WordResponse> findSearchComplete(String word) {
        return trie.searchComplete(word);
    }

    public void deleteWord(String word) {
        trie.delete(word);
    }
}