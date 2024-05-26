package com.team.saver.partner.request.entity;

import com.team.saver.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRecommender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long partnerRecommenderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private PartnerRequest partnerRequest;

    public static PartnerRecommender createEntity(Account account, PartnerRequest request) {
        return PartnerRecommender.builder()
                .account(account)
                .partnerRequest(request)
                .build();
    }
}
