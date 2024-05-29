package com.team.saver.search.popular.util;

import com.team.saver.search.popular.dto.SearchWordScoreDto;
import com.team.saver.search.popular.entity.SearchWord;
import com.team.saver.search.popular.repository.SearchWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SearchWordScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final SearchWordRepository searchWordRepository;
    private final SearchWordScore searchWordScore;

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    @Transactional
    public void updateSearchWord_everyTime() {
        resetSearchWord_everyTime();

        Set<String> keys = redisTemplate.keys("searchWord*");

        for (String key : keys) {
            String[] parts = key.split("_"); // 0 - userEmail, 1 - searchWord
            String searchWord = parts[1];

            SearchWord searchWordEntity = searchWordRepository.findBySearchWord(searchWord)
                    .orElseGet(() -> searchWordRepository.save(SearchWord.createEntity(searchWord)));
            searchWordEntity.updateSearch();

            redisTemplate.delete(key);
        }

        updateSearchWordScore();

        calculateRankingChangeValue();
    }

    public void updateSearchWordScore() {
        searchWordScore.clearQueue();
        List<SearchWord> searchWordList = searchWordRepository.findAll();

        for(SearchWord searchWord : searchWordList) {
            addSearchWordScore(searchWord);
        }
    }

    private void addSearchWordScore(SearchWord searchWord) {
        double score = WeightValue.calculated(searchWord);

        searchWordScore.addSearchWord(searchWord.getSearchWordId(), searchWord.getSearchWord() , score);
    }

    @Transactional
    public void calculateRankingChangeValue() {
        List<SearchWordScoreDto.Node> temp = new ArrayList<>();

        int rankingIndex = 1;

        while(!searchWordScore.isEmpty()) {
            SearchWordScoreDto.Node searchWordDto = searchWordScore.getSearchWord();

            SearchWord searchWord = searchWordRepository.findBySearchWord(searchWordDto.getSearchWord()).get();

            temp.add(new SearchWordScoreDto.Node(searchWord.getSearchWordId(),rankingIndex, searchWordDto , searchWord.getPreviousRanking() - rankingIndex));
            searchWord.setPreviousRanking(rankingIndex);

            rankingIndex += 1;
        }

        searchWordScore.clearQueue();
        searchWordScore.initQueue(temp);
    }

    @Transactional
    public void resetSearchWord_everyTime() {
        searchWordRepository.resetRecentlySearch();
    }

    @Scheduled(cron = "0 0 0 1/1 * ?")
    @Transactional
    public void resetSearchWord_everyDay() {
        searchWordRepository.resetDaySearch();
    }

    @Scheduled(cron = "0 0 0 ? * MON")
    @Transactional
    public void resetSearchWord_everyWeek() {
        searchWordRepository.resetWeekSearch();
    }

}