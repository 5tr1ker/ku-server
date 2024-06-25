package com.team.saver.partner.response.repository;

import com.team.saver.partner.response.entity.PartnerResponse;

import java.util.Optional;

public interface CustomPartnerResponseRepository {

    Optional<PartnerResponse> findByEmailAndResponseId(String email, long responseId);

}
