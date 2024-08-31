package com.team.saver.partner.request.repository;

import com.team.saver.partner.request.dto.PartnerRequestDetailResponse;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomPartnerRequestRepository {

    List<PartnerRequestResponse> findAllEntity(String email, Pageable pageable);

    Optional<PartnerRequestDetailResponse> findDetailById(long partnerRequestId, String email);

    Optional<PartnerRecommender> findRecommenderByEmailAndRequestId(String email, long partnerRequestId);

    long findTotalPartnerRequestCount();

    List<PartnerRequestResponse> findMostRecommend(String email, long size);

    Optional<PartnerRequest> findByIdAndAccountEmail(String email, long partnerRequestId);

}
