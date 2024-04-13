package com.team.saver.partner.response.entity;

import com.team.saver.partner.request.entity.PartnerRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long partnerResponseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(nullable = false)
    private PartnerRequest partnerRequest;

    @Column(nullable = false)
    private String message;

    public static PartnerResponse createEntity(String message) {
        return PartnerResponse.builder()
                .message(message)
                .build();
    }

}
