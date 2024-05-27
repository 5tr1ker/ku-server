package com.team.saver.partner.response.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.partner.request.entity.PartnerRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime writeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account writer;

    public static PartnerResponse createEntity(String message, Account account) {
        return PartnerResponse.builder()
                .message(message)
                .writer(account)
                .build();
    }

}
