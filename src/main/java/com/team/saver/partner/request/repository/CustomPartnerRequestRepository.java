package com.team.saver.partner.request.repository;

import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomPartnerRequestRepository {

    List<PartnerRequestResponse> findAllEntity(Pageable pageable);

    Optional<PartnerRecommender> findRecommenderByEmailAndRequestId(String email, long partnerRequestId);
}
