package com.team.saver.partner.response.entity;

import com.team.saver.partner.request.entity.PartnerRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponse {

    @Id @GeneratedValue
    private long partnerResponseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    private PartnerRequest partnerRequest;

    private String message;

    public static PartnerResponse createPartnerResponse(String message) {
        return PartnerResponse.builder()
                .message(message)
                .build();
    }

}
