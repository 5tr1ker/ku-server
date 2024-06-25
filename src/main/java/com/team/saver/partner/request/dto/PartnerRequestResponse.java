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

    private String requestMarketName;

    private String marketAddress;

    private long requestUserKey;

    private String requestUserEmail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime writeTime;

    private long recommendCount;

    private List<PartnerCommentResponse> resultMessage;

    public static PartnerRequestResponse createResponse(PartnerRequest request) {
        List<PartnerCommentResponse> messageList = request.getPartnerComment().stream()
                .map(PartnerCommentResponse::createResponseData)
                .collect(Collectors.toList());

        return PartnerRequestResponse.builder()
                .partnerRequestId(request.getPartnerRequestId())
                .requestMarketName(request.getRequestMarketName())
                .marketAddress(request.getMarketAddress())
                .requestUserKey(request.getRequestUser().getAccountId())
                .requestUserEmail(request.getRequestUser().getEmail())
                .writeTime(request.getWriteTime())
                .recommendCount(request.getPartnerRecommenders().size())
                .resultMessage(messageList)
                .build();
    }

}