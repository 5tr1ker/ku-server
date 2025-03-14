package com.team.saver.event.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.dto.LogInNotEssential;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.event.dto.EventDetailResponse;
import com.team.saver.event.dto.EventCreateRequest;
import com.team.saver.event.dto.EventResponse;
import com.team.saver.event.dto.EventUpdateRequest;
import com.team.saver.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/v1/events")
    @Operation(summary = "[ 비 - 로그인 ] 이벤트 데이터 가져오기 ( 20 )")
    public ResponseEntity findEvent(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                    @RequestParam boolean isParticipant,
                                    NoOffset noOffset) {
        List<EventResponse> result = eventService.findEvent(currentUser, isParticipant, noOffset);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/events/{eventId}")
    @Operation(summary = "특정 이벤트의 세부 데이터 가져오기 ( 21 )")
    public ResponseEntity findEventDetail(@PathVariable long eventId) {
        EventDetailResponse result = eventService.findEventDetail(eventId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/events")
    @Operation(summary = "이벤트 데이터 추가 ( 22 )")
    public ResponseEntity addEvent(@RequestPart EventCreateRequest request,
                                   @RequestPart MultipartFile image) {
        eventService.addEvent(request, image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/v1/events/{eventId}")
    @Operation(summary = "이벤트 데이터 수정 ( 23 )")
    public ResponseEntity updateEvent(@PathVariable long eventId,
                                      @RequestPart EventUpdateRequest request,
                                      @RequestPart(required = false) MultipartFile image) {
        eventService.updateEvent(eventId, request, image);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/v1/events/{eventId}")
    @Operation(summary = "이벤트 데이터 삭제 ( 24 )")
    public ResponseEntity deleteEvent(@PathVariable long eventId) {
        eventService.deleteEvent(eventId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/v1/events/{eventId}/participation")
    @Operation(summary = "[ 로그인 ] 이벤트 참여 ( 122 )")
    public ResponseEntity participateEvent(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                           @PathVariable long eventId) {
        eventService.participateEvent(currentUser, eventId);

        return ResponseEntity.ok().build();
    }

}
