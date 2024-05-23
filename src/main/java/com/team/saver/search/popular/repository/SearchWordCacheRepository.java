package com.team.saver.search.popular.repository;

import com.team.saver.search.popular.entity.SearchWordCache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SearchWordCacheRepository extends JpaRepository<SearchWordCache, Long> {

    boolean existsByUserIpAndSearchTimeAndSearchWord(String userIp, LocalDateTime date, String searchWord);

}
