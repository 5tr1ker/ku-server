package com.team.saver.partner.response.dto;

import com.team.saver.partner.response.entity.PartnerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseData {

    private long responseId;

    private String message;

    public static ResponseData createResponseData(PartnerResponse response) {
        return new ResponseData(response.getPartnerResponseId() , response.getMessage());
    }

}
