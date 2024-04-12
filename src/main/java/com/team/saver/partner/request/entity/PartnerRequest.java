package com.team.saver.partner.request.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

}
