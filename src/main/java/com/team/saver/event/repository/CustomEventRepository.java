package com.team.saver.event.repository;

import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomEventRepository {

    List<EventResponse> findEvent(Pageable pageable);

    EventDetailResponse findEventDetail(long eventId);

}
