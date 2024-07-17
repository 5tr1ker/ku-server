package com.team.saver.partner.request.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.account.entity.QAccount;
import com.team.saver.partner.comment.dto.PartnerCommentResponse;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.entity.QPartnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static com.team.saver.account.entity.QAccount.account;
import static com.team.saver.partner.request.entity.QPartnerRecommender.partnerRecommender;
import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;
import static com.team.saver.partner.comment.entity.QPartnerComment.partnerComment;

@RequiredArgsConstructor
public class PartnerRequestRepositoryImpl implements CustomPartnerRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PartnerRequestResponse> findAllEntity(Pageable pageable) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        PartnerRequestResponse.class,
                        partnerRequest.partnerRequestId,
                        partnerRequest.requestMarketName,
                        partnerRequest.marketAddress,
                        account.accountId,
                        account.email,
                        account.profileImage,
                        partnerRequest.writeTime,
                        select(partnerRecommender.count()).from(partnerRecommender).where(partnerRecommender.partnerRequest.eq(partnerRequest)),
                        partnerComment.count()
                ))
                .from(partnerRequest)
                .innerJoin(partnerRequest.requestUser, account)
                .leftJoin(partnerRequest.partnerComment, partnerComment)
                .groupBy(partnerRequest.partnerRequestId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
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
