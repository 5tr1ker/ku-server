package com.team.saver.partner.comment.repository;

import com.team.saver.partner.comment.entity.PartnerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerCommentRepository extends JpaRepository<PartnerComment, Long>, CustomPartnerCommentRepository {

}
