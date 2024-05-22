package com.team.saver.search.popular.service;

import com.team.saver.search.popular.dto.PopularSearchRequest;
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

    public void addSearchWordToRedis(HttpServletRequest httpServletRequest, PopularSearchRequest request) {
        String userIp = httpServletRequest.getRemoteAddr();
        String userAgent = httpServletRequest.getHeader("User-Agent");
        String today = getNowDateTimeOnlyDateAndHour().toString();

        String key = userIp + "_" + today + "_" + request;

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if (!valueOperations.getOperations().hasKey(key)) {
            valueOperations.set(key, userAgent);
        }
    }

    private LocalDateTime getNowDateTimeOnlyDateAndHour() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        return localDateTime.withMinute(0).withSecond(0).withNano(0);
    }

}
