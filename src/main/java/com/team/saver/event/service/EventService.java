package com.team.saver.event.service;

import com.team.saver.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

}
