package com.team.saver.partner.response.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import com.team.saver.partner.response.dto.NewPartnerResponse;
import com.team.saver.partner.response.entity.PartnerResponse;
import com.team.saver.partner.response.repository.PartnerResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_PARTNER_REQUEST;
import static com.team.saver.common.dto.ErrorMessage.ONLY_DELETE_WRITER;

@Service
@RequiredArgsConstructor
public class PartnerResponseService {

    private final PartnerRequestRepository partnerRequestRepository;
    private final PartnerResponseRepository partnerResponseRepository;
    private final AccountService accountService;

    @Transactional
    public void addPartnerResponse(CurrentUser currentUser ,long partnerRequestId, NewPartnerResponse response) {
        PartnerRequest request = partnerRequestRepository.findById(partnerRequestId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_PARTNER_REQUEST));
        Account account = accountService.getProfile(currentUser);

        PartnerResponse result = PartnerResponse.createEntity(response.getMessage(), account);

        request.addPartnerResponse(result);
    }

    @Transactional
    public void deletePartnerResponse(CurrentUser currentUser, long responseId) {
        PartnerResponse result = partnerResponseRepository.findByEmailAndResponseId(currentUser.getEmail(), responseId)
                .orElseThrow(() -> new CustomRuntimeException(ONLY_DELETE_WRITER));

        partnerResponseRepository.delete(result);
    }
}
