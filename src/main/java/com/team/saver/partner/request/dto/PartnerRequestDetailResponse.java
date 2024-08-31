package com.team.saver.partner.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PartnerRequestDetailResponse {

    private long partnerRequestId;

    private String requestMarketName;

    private String marketAddress;

    private String marketDetailAddress;

    private String marketPhone;

    private String title;

    private String description;

    private long requestUserId;

    private String requestUserEmail;

    private String requestUserImageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private double locationX;

    private double locationY;

    private long recommendationCount;

    private boolean isRecommendation;

}
