package com.team.saver.event.repository;

import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomEventRepository {

    List<EventResponse> findEvent(Pageable pageable);

    Optional<EventDetailResponse> findEventDetail(long eventId);

}
