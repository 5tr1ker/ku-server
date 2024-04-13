package com.team.saver.partner.response.service;

import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import com.team.saver.partner.response.dto.NewPartnerResponse;
import com.team.saver.partner.response.entity.PartnerResponse;
import com.team.saver.partner.response.repository.PartnerResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUNT_PARTNER_REQUEST;

@Service
@RequiredArgsConstructor
public class PartnerResponseService {

    private final PartnerRequestRepository partnerRequestRepository;
    private final PartnerResponseRepository partnerResponseRepository;

    @Transactional
    public void addPartnerResponse(NewPartnerResponse response) {
        PartnerRequest request = partnerRequestRepository.findById(response.getPartnerRequestId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUNT_PARTNER_REQUEST));

        PartnerResponse result = PartnerResponse.createEntity(response.getMessage());

        request.addPartnerResponse(result);
    }

    @Transactional
    public void deletePartnerResponse(long responseId) {
        partnerResponseRepository.deleteById(responseId);
    }
}
