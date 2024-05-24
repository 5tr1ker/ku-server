package com.team.saver.search.popular.service;

import com.team.saver.search.popular.dto.PopularSearchRequest;
import com.team.saver.search.popular.util.SearchWordScore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PopularSearchService {

    private final RedisTemplate<String, String> redisTemplate;
    private final SearchWordScore searchWordScore;

    public void addSearchWordToRedis(HttpServletRequest httpServletRequest, PopularSearchRequest request) {
        String userIp = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String today = getNowDateTimeOnlyDateAndHour().toString();

        String key = "searchWord=" + userIp + "_" + today + "_" + request.getSearchWord();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (!valueOperations.getOperations().hasKey(key)) {
            valueOperations.set(key, userAgent);
        }
    }

    public void getPopularSearchWord() {

    }

    private LocalDateTime getNowDateTimeOnlyDateAndHour() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return localDateTime;
    }

    private LocalDate getNowDateOnlyDateAndHour() {
        LocalDate localDate = LocalDate.now(ZoneId.of("Asia/Seoul"));

        return localDate;
    }

}
