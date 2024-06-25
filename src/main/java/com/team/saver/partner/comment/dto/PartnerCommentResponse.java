package com.team.saver.partner.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.partner.comment.entity.PartnerComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PartnerCommentResponse {

    private long responseId;

    private String message;

    private String writer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    public static PartnerCommentResponse createResponseData(PartnerComment response) {
        return new PartnerCommentResponse(response.getPartnerCommentId()
                , response.getMessage()
                , response.getWriter().getEmail()
                , response.getWriteTime());
    }

}
