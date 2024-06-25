package com.team.saver.partner.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.saver.partner.comment.entity.PartnerComment;
import lombok.RequiredArgsConstructor;

import static com.team.saver.partner.comment.entity.QPartnerComment.partnerComment;
import static com.team.saver.account.entity.QAccount.account;
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
}
