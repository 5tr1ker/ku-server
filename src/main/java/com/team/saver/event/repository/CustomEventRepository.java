package com.team.saver.event.repository;

import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventResponse;
import com.team.saver.event.entity.EventParticipation;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomEventRepository {

    List<EventResponse> findEvent(String email, boolean isParticipant, Pageable pageable);

    List<EventResponse> findEvent(Pageable pageable);

    Optional<EventDetailResponse> findEventDetail(long eventId);

    Optional<EventParticipation> findParticipationByEmailAndId(String email, long eventId);

}
