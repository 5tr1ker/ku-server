package com.team.saver.search.popular.util;

import com.team.saver.search.popular.entity.SearchWordCache;
import com.team.saver.search.popular.repository.SearchWordCacheRepository;
import com.team.saver.search.popular.repository.SearchWordRepository;
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
    private final SearchWordCacheRepository searchWordCacheRepository;
    private final SearchWordRepository searchWordRepository;

    @Scheduled(cron = "0 0 0/1 1/1 * ? *")
    public void updateSearchWord_everyTime() {
        Set<String> keys = redisTemplate.keys("*_*");

        for (String key : keys) {
            String[] parts = key.split("_");
            String userIp = parts[0];
            LocalDateTime date = LocalDateTime.parse(parts[1]);
            String searchWord = parts[2];

            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String userAgent = valueOperations.get(key);

            if(!searchWordCacheRepository.existsByUserIpAndSearchTimeAndSearchWord(userIp, date, searchWord)){
                SearchWordCache searchWordCache = SearchWordCache.builder()
                        .userIp(userIp)
                        .userAgent(userAgent)
                        .searchTime(date)
                        .searchWord(searchWord)
                        .build();

                searchWordCacheRepository.save(searchWordCache);
            }

            redisTemplate.delete(key);
        }
    }

    private boolean isRecentlySearchWord(LocalDateTime localDateTime) {
        LocalDateTime now = LocalDateTime.now().minusHours(1);

        if(now.compareTo(localDateTime) >= 0) {
            return true;
        }

        return false;
    }

    public void resetSearchWord_everyTime() {
        searchWordRepository.resetRecentlySearch();
    }

    @Scheduled(cron = "0 0 0 1/1 * ? *")
    public void resetSearchWord_everyDay() {
        searchWordRepository.resetDaySearch();
    }

    @Scheduled(cron = "0 0 0 ? * MON *")
    public void resetSearchWord_everyWeek() {
        searchWordRepository.resetWeekSearch();
    }

}
