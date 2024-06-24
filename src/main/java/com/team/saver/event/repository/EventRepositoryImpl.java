package com.team.saver.event.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.team.saver.event.entity.QEvent.event;

@RequiredArgsConstructor
public class EventRepositoryImpl implements CustomEventRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<EventResponse> findEvent(Pageable pageable) {
        return jpaQueryFactory.select(
                        Projections.constructor(EventResponse.class,
                                event.eventId,
                                event.title,
                                event.imageUrl,
                                event.eventStartDate,
                                event.eventEndDate
                        )
                ).from(event)
                .where(event.isDelete.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public EventDetailResponse findEventDetail(long eventId) {
        return jpaQueryFactory.select(
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
    }
}
