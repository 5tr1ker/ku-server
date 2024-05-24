package com.team.saver.search.popular.repository;

import com.team.saver.search.popular.entity.SearchWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SearchWordRepository extends JpaRepository<SearchWord, Long> , CustomSearchWordRepository{

    Optional<SearchWord> findBySearchWord(String searchWord);

}
