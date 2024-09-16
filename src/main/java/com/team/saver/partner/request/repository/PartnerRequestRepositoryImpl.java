package com.team.saver.partner.request.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.entity.QAccount;
import com.team.saver.partner.request.dto.PartnerRequestDetailResponse;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.entity.QPartnerRecommender;
import com.team.saver.partner.request.entity.QPartnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.partner.comment.entity.QPartnerComment.partnerComment;
import static com.team.saver.partner.request.entity.QPartnerRecommender.partnerRecommender;
import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;

@RequiredArgsConstructor
public class PartnerRequestRepositoryImpl implements CustomPartnerRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartnerRequestResponse> findAllEntity(String email, Pageable pageable) {
        List<Long> idList = jpaQueryFactory.select(partnerRequest.partnerRequestId)
                .from(partnerRequest)
                .orderBy(partnerRequest.writeTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return jpaQueryFactory
                .select(Projections.constructor(
                        PartnerRequestResponse.class,
                        partnerRequest.partnerRequestId,
                        partnerRequest.title,
                        partnerRequest.description,
                        partnerRequest.writeTime,
                        select(partnerRecommender.count()).from(partnerRecommender).where(partnerRecommender.partnerRequest.eq(partnerRequest)),
                        partnerComment.count(),
                        partnerRecommender.isNotNull()
                ))
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account)
                .leftJoin(partnerRequest.partnerComment, partnerComment)
                .leftJoin(partnerRequest.partnerRecommenders, partnerRecommender).on(partnerRecommender.account.email.eq(email))
                .groupBy(partnerRequest.partnerRequestId)
                .orderBy(partnerRequest.writeTime.desc())
                .where(partnerRequest.partnerRequestId.in(idList))
                .fetch();
    }

    @Override
    public Optional<PartnerRequestDetailResponse> findDetailById(long partnerRequestId, String email) {
        PartnerRequestDetailResponse result = jpaQueryFactory.select(
                        Projections.constructor(PartnerRequestDetailResponse.class,
                                partnerRequest.partnerRequestId,
                                partnerRequest.requestMarketName,
                                partnerRequest.marketAddress,
                                partnerRequest.detailAddress,
                                partnerRequest.phoneNumber,
                                partnerRequest.title,
                                partnerRequest.description,
                                account.accountId,
                                account.email,
                                account.profileImage,
                                partnerRequest.writeTime,
                                partnerRequest.locationX,
                                partnerRequest.locationY,
                                partnerRequest.imageUrl,
                                select(partnerRecommender.count()).from(partnerRecommender).where(partnerRecommender.partnerRequest.eq(partnerRequest)),
                                partnerRecommender.isNotNull()
                        )
                ).from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account)
                .leftJoin(partnerRequest.partnerRecommenders, partnerRecommender).on(partnerRecommender.account.email.eq(email))
                .where(partnerRequest.partnerRequestId.eq(partnerRequestId))
                .fetchOne();

        return Optional.ofNullable(result);
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

    @Override
    public long findTotalPartnerRequestCount() {
        return jpaQueryFactory.select(partnerRequest.count())
                .from(partnerRequest)
                .fetchOne();
    }

    @Override
    public List<PartnerRequestResponse> findMostRecommend(String email, long size) {
        QAccount qAccount = new QAccount("qAccount2");
        QPartnerRequest qPartnerRequest = new QPartnerRequest("qPartnerRequest2");
        StringTemplate castExpression = Expressions.stringTemplate("CONVERT({0}, CHAR(255))", email);

        return jpaQueryFactory
                .select(Projections.constructor(
                        PartnerRequestResponse.class,
                        partnerRequest.partnerRequestId,
                        partnerRequest.title,
                        partnerRequest.description,
                        partnerRequest.writeTime,
                        partnerRecommender.count(),
                        select(partnerComment.count()).from(partnerComment).where(partnerComment.partnerRequest.eq(partnerRequest)),
                        select(partnerRecommender.isNotNull())
                                .from(partnerRecommender)
                                .innerJoin(partnerRecommender.partnerRequest, qPartnerRequest).on(qPartnerRequest.eq(partnerRequest))
                                .innerJoin(partnerRecommender.account, qAccount).on(castExpression.eq(qAccount.email))
                ))
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account)
                .leftJoin(partnerRequest.partnerRecommenders, partnerRecommender)
                .groupBy(partnerRequest.partnerRequestId)
                .orderBy(partnerRecommender.count().desc())
                .limit(size)
                .fetch();
    }

    @Override
    public Optional<PartnerRequest> findByIdAndAccountEmail(String email, long partnerRequestId) {
        PartnerRequest result = jpaQueryFactory.select(partnerRequest)
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account).on(account.email.eq(email))
                .where(partnerRequest.partnerRequestId.eq(partnerRequestId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
