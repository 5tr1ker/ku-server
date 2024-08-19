package com.team.saver.market.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ReviewResponse {

    private long reviewId;

    private long reviewerId;

    private String reviewerEmail;

    private String reviewerImage;

    private String reviewContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeDate;

    private long marketId;

    private String marketName;

    private int score;

    private long recommendCount;

    private List<ReviewImageResponse> images;

}
