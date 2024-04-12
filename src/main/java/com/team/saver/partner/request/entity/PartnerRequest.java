package com.team.saver.partner.request.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.partner.request.dto.NewPartnerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {

    @Id @GeneratedValue
    private long partnerRequestId;

    private String requestMarketName;

    private String marketAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account requestUser;

    public static PartnerRequest createPartnerRequest(Account account, NewPartnerRequest request) {
        return PartnerRequest.builder()
                .requestMarketName(request.getRequestMarketName())
                .marketAddress(request.getMarketAddress())
                .requestUser(account)
                .build();
    }
}
