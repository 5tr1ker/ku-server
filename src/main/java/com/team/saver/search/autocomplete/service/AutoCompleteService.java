package com.team.saver.search.autocomplete.service;

import com.team.saver.search.autocomplete.dto.AutoCompleteRequest;
import com.team.saver.search.autocomplete.dto.UtilInitDto;
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
    public void addSearchWord(AutoCompleteRequest request) {
        trie.insert(request.getWord());
    }

    public List<UtilInitDto> findSearchComplete(String word) {
        return trie.searchComplete(word);
    }
}