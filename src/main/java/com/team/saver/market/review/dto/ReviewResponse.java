package com.team.saver.market.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String orderMenu;

    private int score;

    private long recommendCount;

    private boolean isRecommendation;

    private Set<ReviewImageResponse> images;

    public ReviewResponse(long reviewId, long reviewerId, String reviewerEmail, String reviewerImage, String reviewContent, LocalDateTime writeDate, long marketId, String marketName, String orderMenu, int score, long recommendCount, boolean isRecommendation, List<ReviewImageResponse> images) {
        this.reviewId = reviewId;
        this.reviewerId = reviewerId;
        this.reviewerEmail = reviewerEmail;
        this.reviewerImage = reviewerImage;
        this.reviewContent = reviewContent;
        this.writeDate = writeDate;
        this.marketId = marketId;
        this.marketName = marketName;
        this.orderMenu = orderMenu;
        this.score = score;
        this.recommendCount = recommendCount;
        this.isRecommendation = isRecommendation;
        this.images = new HashSet<>(images);
    }
}
