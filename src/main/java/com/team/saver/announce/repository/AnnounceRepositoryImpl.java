package com.team.saver.announce.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.announce.dto.AnnounceDetailResponse;
import com.team.saver.announce.dto.AnnounceResponse;
import com.team.saver.announce.entity.Announce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.announce.entity.QAnnounce.announce;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class AnnounceRepositoryImpl implements CustomAnnounceRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AnnounceResponse> findAllAnnounce(Pageable pageable, boolean isImportant) {
        List<Long> idList = jpaQueryFactory.select(announce.announceId)
                .from(announce)
                .where(announce.isImportant.eq(isImportant).and(announce.isDelete.eq(false)))
                .orderBy(announce.announceId.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return jpaQueryFactory.select(
                        Projections.constructor(
                                AnnounceResponse.class,
                                announce.announceId,
                                announce.writeTime,
                                announce.title,
                                announce.announceType,
                                announce.isImportant
                        )
                ).from(announce)
                .orderBy(announce.announceId.desc())
                .where(announce.announceId.in(idList))
                .fetch();
    }

    @Override
    public Optional<AnnounceDetailResponse> findAnnounceDetail(long announceId) {
        AnnounceDetailResponse result = jpaQueryFactory.select(
                        Projections.constructor(
                                AnnounceDetailResponse.class,
                                announce.announceId,
                                announce.writeTime,
                                announce.title,
                                announce.description,
                                announce.announceType,
                                announce.isImportant
                        )
                ).from(announce)
                .where(announce.announceId.eq(announceId).and(announce.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Announce> findById(long announceId) {
        Announce result = jpaQueryFactory.select(announce)
                .from(announce)
                .where(announce.announceId.eq(announceId).and(announce.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
