package com.team.saver.partner.comment.repository;

import com.team.saver.partner.comment.dto.PartnerCommentResponse;
import com.team.saver.partner.comment.entity.PartnerComment;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomPartnerCommentRepository {

    Optional<PartnerComment> findByEmailAndCommentId(String email, long commentId);

    List<PartnerCommentResponse> findByPartnerRequestId(long partnerRequestId, Pageable pageable);

}
