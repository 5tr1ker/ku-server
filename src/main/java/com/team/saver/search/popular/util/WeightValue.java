package com.team.saver.search.popular.util;

import com.team.saver.search.popular.entity.SearchWord;

public class WeightValue {

    public static double calculated(SearchWord searchWord) {
        return calculated(searchWord.getRecentlySearch(),
                searchWord.getTotalSearch(),
                searchWord.getDaySearch(),
                searchWord.getWeekSearch());
    }

    public static double calculated(double recentlySearch,
                                    double totalSearch,
                                    double daySearch,
                                    double weekSearch) {

        double recentlyScore = (recentlySearch * 0.15) + (totalSearch * 0.05);
        double dayScore = (daySearch * 0.12) + (totalSearch * 0.04);
        double weekScore = (weekSearch * 0.04) + (totalSearch * 0.03);

        return recentlyScore + weekScore + dayScore;
    }
}
