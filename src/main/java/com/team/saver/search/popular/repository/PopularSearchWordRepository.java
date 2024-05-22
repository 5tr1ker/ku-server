package com.team.saver.search.popular.repository;

import com.team.saver.search.popular.entity.PopularSearchWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PopularSearchWordRepository extends JpaRepository<PopularSearchWord, Long> {

    boolean existsByUserIpAndSearchTimeAndSearchWord(String userIp, LocalDateTime date, String searchWord);

}
