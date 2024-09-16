package com.team.saver.event.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventResponse;
import com.team.saver.event.entity.EventParticipation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.event.entity.QEventParticipation.eventParticipation;
import static com.team.saver.event.entity.QEvent.event;

@RequiredArgsConstructor
public class EventRepositoryImpl implements CustomEventRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EventResponse> findEvent(String email, boolean isParticipant, NoOffset noOffset) {
        JPAQueryBase query = jpaQueryFactory.select(
                        Projections.constructor(EventResponse.class,
                                event.eventId,
                                event.title,
                                event.imageUrl,
                                event.eventStartDate,
                                event.eventEndDate
                        )
                ).from(event)
                .where(event.isDelete.eq(false).and(event.eventId.gt(noOffset.getLastIndex())))
                .limit(noOffset.getSize());

        if (isParticipant) {
            query.leftJoin(event.eventParticipants, eventParticipation)
                    .innerJoin(eventParticipation.account, account).on(account.email.eq(email));
        } else {
            query.where(event.notIn(
                    select(event)
                            .from(eventParticipation)
                            .innerJoin(eventParticipation.account, account).on(account.email.eq(email))
                            .innerJoin(eventParticipation.event, event)
            ));
        }

        return query.fetch();
    }

    @Override
    public List<EventResponse> findEvent(NoOffset noOffset) {
        return jpaQueryFactory.select(
                        Projections.constructor(EventResponse.class,
                                event.eventId,
                                event.title,
                                event.imageUrl,
                                event.eventStartDate,
                                event.eventEndDate
                        )
                ).from(event)
                .where(event.isDelete.eq(false).and(event.eventId.gt(noOffset.getLastIndex())))
                .limit(noOffset.getSize())
                .fetch();
    }

    @Override
    public Optional<EventDetailResponse> findEventDetail(long eventId) {
        EventDetailResponse result = jpaQueryFactory.select(
                        Projections.constructor(EventDetailResponse.class,
                                event.eventId,
                                event.title,
                                event.description,
                                event.imageUrl,
                                event.eventStartDate,
                                event.eventEndDate
                        )
                ).from(event)
                .where(event.eventId.eq(eventId).and(event.isDelete.eq(false)))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<EventParticipation> findParticipationByEmailAndId(String email, long eventId) {
        EventParticipation result = jpaQueryFactory.select(eventParticipation)
                .from(eventParticipation)
                .innerJoin(eventParticipation.event, event).on(event.eventId.eq(eventId))
                .innerJoin(eventParticipation.account, account).on(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
