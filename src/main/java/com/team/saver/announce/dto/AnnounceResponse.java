package com.team.saver.announce.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.announce.entity.AnnounceType;
import jakarta.persistence.Column;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class AnnounceResponse {

    private long announceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private String title;

    private String description;

    private AnnounceType announceType;

    private boolean isImportant;

}
