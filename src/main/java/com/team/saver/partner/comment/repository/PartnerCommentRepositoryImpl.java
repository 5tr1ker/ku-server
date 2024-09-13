package com.team.saver.partner.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.comment.dto.PartnerCommentResponse;
import com.team.saver.partner.comment.entity.PartnerComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import static com.team.saver.partner.request.entity.QPartnerRequest.partnerRequest;
import static com.team.saver.partner.comment.entity.QPartnerComment.partnerComment;
import static com.team.saver.account.entity.QAccount.account;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PartnerCommentRepositoryImpl implements CustomPartnerCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<PartnerComment> findByEmailAndCommentId(String email, long responseId) {
        PartnerComment result = jpaQueryFactory.select(partnerComment)
                .from(partnerComment)
                .innerJoin(partnerComment.writer, account).on(account.email.eq(email))
                .where(partnerComment.partnerCommentId.eq(responseId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<PartnerCommentResponse> findByPartnerRequestId(long partnerRequestId, Pageable pageable) {

        return jpaQueryFactory.select(
                        Projections.constructor(
                                PartnerCommentResponse.class,
                                partnerComment.partnerCommentId,
                                partnerComment.message,
                                account.email,
                                account.profileImage,
                                partnerComment.writeTime
                        )
                ).from(partnerComment)
                .innerJoin(partnerComment.writer, account)
                .innerJoin(partnerComment.partnerRequest, partnerRequest).on(partnerRequest.partnerRequestId.eq(partnerRequestId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
