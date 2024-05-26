package com.team.saver.partner.request.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.dto.NewPartnerRequest;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.EXIST_RECOMMENDER;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_PARTNER_REQUEST;

@Service
@RequiredArgsConstructor
public class PartnerRequestService {

    private final PartnerRequestRepository partnerRequestRepository;
    private final AccountService accountService;

    @Transactional
    public void requestNewPartner(NewPartnerRequest request, CurrentUser currentUser) {
        Account account = accountService.getProfile(currentUser);

        PartnerRequest partnerRequest = PartnerRequest.createEntity(account, request);
        partnerRequestRepository.save(partnerRequest);
    }

    public List<PartnerRequestResponse> findAllEntity() {
        return partnerRequestRepository.findAllEntity();
    }

    @Transactional
    public void requestPartnerRecommendation(CurrentUser currentUser, long partnerRequestId) {
        if(partnerRequestRepository.findRecommenderByEmailAndRequestId(currentUser.getEmail(), partnerRequestId).isPresent()) {
            throw new CustomRuntimeException(EXIST_RECOMMENDER);
        }

        Account account = accountService.getProfile(currentUser);
        PartnerRequest partnerRequest = partnerRequestRepository.findById(partnerRequestId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_PARTNER_REQUEST));

        PartnerRecommender partnerRecommender = PartnerRecommender.createEntity(account, partnerRequest);
        partnerRequest.addPartnerRecommender(partnerRecommender);
    }

}
