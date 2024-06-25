package com.team.saver.partner.request.entity;

import com.team.saver.account.entity.Account;
import com.team.saver.partner.comment.entity.PartnerComment;
import com.team.saver.partner.request.dto.PartnerRequestCreateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String requestMarketName;

    @Column(nullable = false)
    private String marketAddress;

    @Column(nullable = false)
    private String detailAddress;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account requestUser;

    @CreationTimestamp
    private LocalDateTime writeTime;

    @OneToMany(mappedBy = "partnerRequest", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<PartnerComment> partnerComment = new ArrayList<>();

    @OneToMany(mappedBy = "partnerRequest", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<PartnerRecommender> partnerRecommenders = new ArrayList<>();

    public static PartnerRequest createEntity(Account account, PartnerRequestCreateRequest request) {
        return PartnerRequest.builder()
                .requestMarketName(request.getRequestMarketName())
                .marketAddress(request.getMarketAddress())
                .requestUser(account)
                .detailAddress(request.getDetailAddress())
                .title(request.getTitle())
                .description(request.getDescription())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public void addPartnerResponse(PartnerComment response) {
        partnerComment.add(response);
        response.setPartnerRequest(this);
    }

    public void addPartnerRecommender(PartnerRecommender partnerRecommender) {
        partnerRecommenders.add(partnerRecommender);
        partnerRecommender.setPartnerRequest(this);
    }

}
