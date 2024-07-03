package com.team.saver.announce.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<AnnounceResponse> findAllAnnounce(Pageable pageable) {
        NumberTemplate<Integer> isImportantAsNumber = Expressions.numberTemplate(Integer.class, "case when {0} then 1 else 0 end", announce.isImportant);

        return jpaQueryFactory.select(
                        Projections.constructor(
                                AnnounceResponse.class,
                                announce.announceId,
                                announce.writeTime,
                                announce.title,
                                announce.description,
                                announce.announceType,
                                announce.isImportant
                        )
                ).from(announce)
                .orderBy(isImportantAsNumber.desc(), announce.announceId.desc())
                .where(announce.isDelete.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Optional<AnnounceResponse> findAnnounceDetail(long announceId) {
        AnnounceResponse result = jpaQueryFactory.select(
                        Projections.constructor(
                                AnnounceResponse.class,
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
