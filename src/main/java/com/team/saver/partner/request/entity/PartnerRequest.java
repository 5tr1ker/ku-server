package com.team.saver.partner.request.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.partner.request.dto.NewPartnerRequest;
import com.team.saver.partner.response.entity.PartnerResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long partnerRequestId;

    private String requestMarketName;

    private String marketAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account requestUser;

    @OneToMany(mappedBy = "partnerRequest", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<PartnerResponse> partnerResponse = new ArrayList<>();

    public static PartnerRequest createEntity(Account account, NewPartnerRequest request) {
        return PartnerRequest.builder()
                .requestMarketName(request.getRequestMarketName())
                .marketAddress(request.getMarketAddress())
                .requestUser(account)
                .build();
    }

    public void addPartnerResponse(PartnerResponse response) {
        partnerResponse.add(response);
        response.setPartnerRequest(this);
    }

}
