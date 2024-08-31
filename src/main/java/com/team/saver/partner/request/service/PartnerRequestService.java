package com.team.saver.partner.request.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.service.AccountService;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.dto.PartnerRequestCreateRequest;
import com.team.saver.partner.request.dto.PartnerRequestDetailResponse;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.dto.PartnerRequestUpdateRequest;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.repository.PartnerRecommenderRepository;
import com.team.saver.partner.request.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class PartnerRequestService {

    private final PartnerRequestRepository partnerRequestRepository;
    private final AccountService accountService;
    private final PartnerRecommenderRepository partnerRecommenderRepository;

    @Transactional
    public void requestNewPartner(PartnerRequestCreateRequest request, CurrentUser currentUser) {
        Account account = accountService.getProfile(currentUser);

        PartnerRequest partnerRequest = PartnerRequest.createEntity(account, request);
        partnerRequestRepository.save(partnerRequest);
    }

    public List<PartnerRequestResponse> findAllEntity(CurrentUser currentUser, Pageable pageable) {
        return partnerRequestRepository.findAllEntity(currentUser.getEmail(), pageable);
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

    public PartnerRequestDetailResponse findDetailById(CurrentUser currentUser, long partnerRequestId) {
        return partnerRequestRepository.findDetailById(partnerRequestId, currentUser.getEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_PARTNER_REQUEST));
    }

    public List<PartnerRequestResponse> findMostRecommend(CurrentUser currentUser, long size) {
        return partnerRequestRepository.findMostRecommend(currentUser.getEmail(), size);
    }

    public long findTotalPartnerRequestCount() {
        return partnerRequestRepository.findTotalPartnerRequestCount();
    }

    @Transactional
    public void updatePartnerRequest(CurrentUser currentUser, PartnerRequestUpdateRequest request , long partnerRequestId) {
        PartnerRequest partnerRequest = partnerRequestRepository.findByIdAndAccountEmail(currentUser.getEmail(), partnerRequestId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_PARTNER_REQUEST));

        partnerRequest.update(request);
    }

    @Transactional
    public void deletePartnerRequest(CurrentUser currentUser, long partnerRequestId) {
        PartnerRequest partnerRequest = partnerRequestRepository.findByIdAndAccountEmail(currentUser.getEmail(), partnerRequestId)
            .orElseThrow(() -> new CustomRuntimeException(ONLY_DELETE_WRITER));

        partnerRequestRepository.delete(partnerRequest);
    }

    @Transactional
    public void deleteRecommendation(CurrentUser currentUser, long partnerRequestId) {
        PartnerRecommender result = partnerRequestRepository.findRecommenderByEmailAndRequestId(currentUser.getEmail(), partnerRequestId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND_RECOMMENDATION));

        partnerRecommenderRepository.delete(result);
    }
}
