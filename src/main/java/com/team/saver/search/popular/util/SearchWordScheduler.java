package com.team.saver.search.popular.util;

import com.team.saver.search.popular.entity.SearchWordCache;
import com.team.saver.search.popular.repository.PopularSearchWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SearchWordScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final PopularSearchWordRepository popularSearchWordRepository;

    @Scheduled(cron = "0 0 0/1 1/1 * ? *")
    public void updateSearchWord() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDateTime date = LocalDateTime.parse(parts[1]);
            String searchWord = parts[2];

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!popularSearchWordRepository.existsByUserIpAndSearchTimeAndSearchWord(userIp, date, searchWord)){
                SearchWordCache searchWordCache = SearchWordCache.builder()
                        .userIp(userIp)
                        .userAgent(userAgent)
                        .searchTime(date)
                        .searchWord(searchWord)
                        .build();

                popularSearchWordRepository.save(searchWordCache);
            }

            redisTemplate.delete(key);
        }
    }

    @Scheduled(cron = "0 0 0 1/1 * ? *")
    public void

    @Scheduled(cron = "0 0 0 ? * MON *")
    public

}
