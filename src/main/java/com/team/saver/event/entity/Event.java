package com.team.saver.event.entity;

import com.team.saver.event.dto.EventCreateRequest;
import com.team.saver.event.dto.EventUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private LocalDate eventStartDate;

    @Column(nullable = false)
    private LocalDate eventEndDate;

    @OneToMany(mappedBy = "event")
    private List<EventParticipation> eventParticipants = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private boolean isDelete = false;

    public void delete() {
        isDelete = true;
    }

    public static Event createEntity(EventCreateRequest request, String imageUrl) {
        return Event.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .eventStartDate(request.getEventStartDate())
                .eventEndDate(request.getEventEndDate())
                .build();
    }

    public void update(EventUpdateRequest request, String imageUrl) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.imageUrl = imageUrl;
        this.eventStartDate = request.getEventStartDate();
        this.eventEndDate = request.getEventEndDate();
    }

}
