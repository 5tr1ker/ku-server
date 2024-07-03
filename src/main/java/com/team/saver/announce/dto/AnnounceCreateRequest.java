package com.team.saver.announce.dto;

import com.team.saver.announce.entity.AnnounceType;
import lombok.Getter;

@Getter
public class AnnounceCreateRequest {

    private String title;

    private String description;

    private AnnounceType announceType;

    private boolean isImportant = false;

}
