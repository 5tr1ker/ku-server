package com.team.saver.search.popular.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.search.popular.dto.PopularSearchRequest;
import com.team.saver.search.popular.dto.SearchWordScoreDto;
import com.team.saver.search.popular.util.SearchWordScore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularSearchService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SearchWordScore searchWordScore;

    public void addSearchWordToRedis(CurrentUser currentUser, PopularSearchRequest request) {
        String key = "searchWord=" + currentUser.getEmail() + "_" + request.getSearchWord();
        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (!valueOperations.getOperations().hasKey(key)) {
            valueOperations.set(key, request.getSearchWord());
        }

    }

    public List<SearchWordScoreDto.Node> getPopularSearchWord() {
        return searchWordScore.getAsList();
    }

}
