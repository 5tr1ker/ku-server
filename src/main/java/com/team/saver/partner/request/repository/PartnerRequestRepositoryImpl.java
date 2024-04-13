package com.team.saver.partner.request.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
}
