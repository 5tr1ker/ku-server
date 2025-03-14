package com.team.saver.search.popular.service;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.search.popular.dto.PopularSearchAddRequest;
import com.team.saver.search.popular.dto.SearchWordScore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopularSearchService {

    private final RedisTemplate<String, String> redisTemplate;
    private final com.team.saver.search.popular.util.SearchWordScore searchWordScore;

    public void addSearchWordToRedis(CurrentUser currentUser, PopularSearchAddRequest request) {
        String key = "searchWord=" + currentUser.getEmail() + "_" + request.getSearchWord();
        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (!valueOperations.getOperations().hasKey(key)) {
            valueOperations.set(key, request.getSearchWord());
        }

    }

    public List<SearchWordScore.Node> getPopularSearchWord(int size) {
        return searchWordScore.getAsList(size);
    }

}
