package com.team.saver.partner.request.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import lombok.RequiredArgsConstructor;

import static com.team.saver.partner.response.entity.QPartnerResponse.partnerResponse;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;

import java.util.List;

@RequiredArgsConstructor
public class PartnerRequestRepositoryImpl implements CustomPartnerRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartnerRequestResponse> findAllEntity() {

        return jpaQueryFactory.select(Projections.constructor(
                        PartnerRequestResponse.class,
                        partnerRequest.partnerRequestId,
                        partnerRequest.requestMarketName,
                        partnerRequest.marketAddress,
                        account.accountId,
                        account.email,
                        Expressions.list(partnerResponse.message)
                ))
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account)
                .leftJoin(partnerRequest.partnerResponse, partnerResponse)
                .fetch();
    }
}
