package com.team.saver.search.popular.repository;

import com.team.saver.search.popular.entity.SearchWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchWordRepository extends JpaRepository<SearchWord, Long> , CustomSearchWordRepository{

}
