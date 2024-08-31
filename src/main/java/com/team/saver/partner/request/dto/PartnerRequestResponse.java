package com.team.saver.partner.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.comment.dto.PartnerCommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class PartnerRequestResponse {

    private long partnerRequestId;

    private String title;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private long recommendCount;

    private long commentCount;

    private boolean isRecommendation;

}