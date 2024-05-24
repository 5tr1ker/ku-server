package com.team.saver.search.popular.repository;

public interface CustomSearchWordRepository {

    long resetRecentlySearch();

    long resetDaySearch();

    long resetWeekSearch();

}
