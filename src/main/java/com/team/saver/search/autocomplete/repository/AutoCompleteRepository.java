package com.team.saver.search.autocomplete.repository;

import com.team.saver.search.autocomplete.entity.AutoComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutoCompleteRepository extends JpaRepository<AutoComplete, Long> {

    Optional<AutoComplete> findByWord(String word);

    long deleteByWord(String word);

}