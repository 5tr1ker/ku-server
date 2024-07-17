package com.team.saver.partner.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.partner.comment.entity.PartnerComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartnerCommentResponse {

    private long commentId;

    private String message;

    private String writer;

    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

}
