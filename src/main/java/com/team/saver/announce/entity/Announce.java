package com.team.saver.announce.entity;

import com.team.saver.announce.dto.AnnounceCreateRequest;
import com.team.saver.announce.dto.AnnounceUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Announce {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long announceId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime writeTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private AnnounceType announceType;

    @Column(nullable = false)
    private boolean isImportant;

    @Builder.Default
    @Column(nullable = false)
    private boolean isDelete = false;

    public static Announce createEntity(AnnounceCreateRequest request) {
        return Announce.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .announceType(request.getAnnounceType())
                .isImportant(request.isImportant())
                .build();
    }

    public void update(AnnounceUpdateRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.announceType = request.getAnnounceType();
        this.isImportant = request.isImportant();
    }

    public void delete() {
        this.isDelete = true;
    }

}
