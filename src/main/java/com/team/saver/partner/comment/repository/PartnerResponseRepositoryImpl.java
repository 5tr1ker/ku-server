package com.team.saver.partner.response.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.response.entity.PartnerResponse;
import lombok.RequiredArgsConstructor;

import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.partner.response.entity.QPartnerResponse.partnerResponse;
import java.util.Optional;

@RequiredArgsConstructor
public class PartnerResponseRepositoryImpl implements CustomPartnerResponseRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PartnerResponse> findByEmailAndResponseId(String email, long responseId) {
        PartnerResponse result = jpaQueryFactory.select(partnerResponse)
                .from(partnerResponse)
                .innerJoin(partnerResponse.writer, account).on(account.email.eq(email))
                .where(partnerResponse.partnerResponseId.eq(responseId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
