package com.team.saver.partner.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.saver.partner.response.entity.PartnerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseData {

    private long responseId;

    private String message;

    private String writer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    public static ResponseData createResponseData(PartnerResponse response) {
        return new ResponseData(response.getPartnerResponseId()
                , response.getMessage()
                , response.getWriter().getEmail()
                , response.getWriteTime());
    }

}
