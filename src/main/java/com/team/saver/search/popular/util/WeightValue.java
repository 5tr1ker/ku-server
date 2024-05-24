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

        totalSearch += 1;
        daySearch += 1;
        weekSearch += 1;
        recentlySearch += 1;

        double weekScore = weekSearch / (totalSearch * 1.2);
        double dayScore = daySearch / (totalSearch * 0.7);
        double recentlyScore = recentlySearch / (totalSearch * 0.3);

        return (recentlyScore + weekScore + dayScore) * Math.pow(2, Math.log10(totalSearch));
    }
}
