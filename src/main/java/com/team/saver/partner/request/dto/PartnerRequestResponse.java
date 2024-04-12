package com.team.saver.partner.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PartnerRequestResponse {

    private long partnerRequestId;

    private String requestMarketName;

    private String marketAddress;

    private long requestUserKey;

    private String requestUserEmail;

}
