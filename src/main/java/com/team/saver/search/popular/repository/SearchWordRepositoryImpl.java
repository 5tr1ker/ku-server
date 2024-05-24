package com.team.saver.search.popular.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.team.saver.search.popular.entity.QSearchWord.searchWord1;

@RequiredArgsConstructor
public class SearchWordRepositoryImpl implements CustomSearchWordRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long resetRecentlySearch() {
        return jpaQueryFactory.update(searchWord1)
                .set(searchWord1.recentlySearch, 0)
                .where(searchWord1.recentlySearch.ne(0))
                .execute();
    }

    @Override
    public long resetDaySearch() {
        return jpaQueryFactory.update(searchWord1)
                .set(searchWord1.daySearch, 0)
                .where(searchWord1.daySearch.ne(0))
                .execute();
    }

    @Override
    public long resetWeekSearch() {
        return jpaQueryFactory.update(searchWord1)
                .set(searchWord1.weekSearch, 0)
                .where(searchWord1.weekSearch.ne(0))
                .execute();
    }
}
