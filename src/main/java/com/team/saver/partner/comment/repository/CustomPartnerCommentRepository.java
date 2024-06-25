package com.team.saver.partner.comment.repository;

import com.team.saver.partner.comment.entity.PartnerComment;

import java.util.Optional;

public interface CustomPartnerCommentRepository {

    Optional<PartnerComment> findByEmailAndCommentId(String email, long commentId);

}
