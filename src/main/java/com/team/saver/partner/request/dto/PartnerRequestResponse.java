package com.team.saver.partner.request.dto;

import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.response.dto.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class PartnerRequestResponse {

    private long partnerRequestId;

    private String requestMarketName;

    private String marketAddress;

    private long requestUserKey;

    private String requestUserEmail;

    private List<ResponseData> resultMessage;

    public static PartnerRequestResponse createResponse(PartnerRequest request) {
        List<ResponseData> messageList = request.getPartnerResponse().stream()
                .map(ResponseData::createResponseData)
                .collect(Collectors.toList());

        return PartnerRequestResponse.builder()
                .partnerRequestId(request.getPartnerRequestId())
                .requestMarketName(request.getRequestMarketName())
                .marketAddress(request.getMarketAddress())
                .requestUserKey(request.getRequestUser().getAccountId())
                .requestUserEmail(request.getRequestUser().getEmail())
                .resultMessage(messageList)
                .build();
    }

}