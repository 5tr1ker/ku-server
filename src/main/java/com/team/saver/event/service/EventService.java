package com.team.saver.event.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventCreateRequest;
import com.team.saver.event.dto.EventResponse;
import com.team.saver.event.dto.EventUpdateRequest;
import com.team.saver.event.entity.Event;
import com.team.saver.event.entity.EventParticipation;
import com.team.saver.event.repository.EventRepository;
import com.team.saver.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.ALREADY_PARTICIPANT_EVENT;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_EVENT;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final S3Service s3Service;
    private final AccountService accountService;

    public List<EventResponse> findEvent(CurrentUser currentUser, boolean isParticipant , NoOffset noOffset) {
        if(currentUser.getEmail().equals("empty")) {
            return eventRepository.findEvent(noOffset);
        }

        return eventRepository.findEvent(currentUser.getEmail(), isParticipant, noOffset);
    }

    public EventDetailResponse findEventDetail(long eventId) {
        return eventRepository.findEventDetail(eventId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_EVENT));
    }

    @Transactional
    public void addEvent(EventCreateRequest request, MultipartFile image) {
        String imageUrl = s3Service.uploadImage(image);
        Event event = Event.createEntity(request, imageUrl);

        eventRepository.save(event);
    }

    @Transactional
    public void updateEvent(long eventId, EventUpdateRequest request, MultipartFile image) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_EVENT));
        String imageUrl = event.getImageUrl();

        if(image != null) {
            s3Service.deleteImage(event.getImageUrl());

            imageUrl = s3Service.uploadImage(image);
        }

        event.update(request, imageUrl);
    }

    @Transactional
    public void deleteEvent(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_EVENT));

        event.delete();
    }

    @Transactional
    public void participateEvent(CurrentUser currentUser, long eventId) {
        if(eventRepository.findParticipationByEmailAndId(currentUser.getEmail(), eventId).isPresent()) {
            throw new CustomRuntimeException(ALREADY_PARTICIPANT_EVENT);
        }

        Account account = accountService.getProfile(currentUser);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_EVENT));

        EventParticipation eventParticipation = EventParticipation.createEntity(account, event);
        event.addParticipant(eventParticipation);
    }

}
