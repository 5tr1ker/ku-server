package com.team.saver.partner.request.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.partner.request.entity.QPartnerRecommender.partnerRecommender;
import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;

@RequiredArgsConstructor
public class PartnerRequestRepositoryImpl implements CustomPartnerRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartnerRequestResponse> findAllEntity() {
        List<PartnerRequest> result = jpaQueryFactory
                .select(partnerRequest)
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser)
                .leftJoin(partnerRequest.partnerResponse)
                .fetch();

        return result.stream()
                .map(PartnerRequestResponse::createResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PartnerRecommender> findRecommenderByEmailAndRequestId(String email, long partnerRequestId) {
        PartnerRecommender result = jpaQueryFactory.select(partnerRecommender)
                .from(partnerRecommender)
                .innerJoin(partnerRecommender.partnerRequest, partnerRequest).on(partnerRequest.partnerRequestId.eq(partnerRequestId))
                .innerJoin(partnerRecommender.account, account).on(account.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
