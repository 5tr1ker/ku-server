package com.team.saver.partner.comment.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.NoOffset;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.comment.dto.PartnerCommentCreateRequest;
import com.team.saver.partner.comment.dto.PartnerCommentResponse;
import com.team.saver.partner.comment.entity.PartnerComment;
import com.team.saver.partner.comment.repository.PartnerCommentRepository;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_PARTNER_REQUEST;
import static com.team.saver.common.dto.ErrorMessage.ONLY_DELETE_WRITER;

@Service
@RequiredArgsConstructor
public class PartnerCommentService {

    private final PartnerRequestRepository partnerRequestRepository;
    private final PartnerCommentRepository partnerCommentRepository;
    private final AccountService accountService;

    @Transactional
    public void addPartnerComment(CurrentUser currentUser , long partnerRequestId, PartnerCommentCreateRequest response) {
        PartnerRequest request = partnerRequestRepository.findById(partnerRequestId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_PARTNER_REQUEST));
        Account account = accountService.getProfile(currentUser);

        PartnerComment result = PartnerComment.createEntity(response.getMessage(), account);

        request.addPartnerComment(result);
    }

    @Transactional
    public void deletePartnerComment(CurrentUser currentUser, long commentId) {
        PartnerComment result = partnerCommentRepository.findByEmailAndCommentId(currentUser.getEmail(), commentId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_DELETE_WRITER));

        partnerCommentRepository.delete(result);
    }

    public List<PartnerCommentResponse> findByPartnerRequestId(long partnerRequestId, NoOffset noOffset) {
        return partnerCommentRepository.findByPartnerRequestId(partnerRequestId, noOffset);
    }

}
