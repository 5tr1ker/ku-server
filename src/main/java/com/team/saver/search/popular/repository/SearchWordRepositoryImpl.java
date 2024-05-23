package com.team.saver.search.popular.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchWordRepositoryImpl implements CustomSearchWordRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
