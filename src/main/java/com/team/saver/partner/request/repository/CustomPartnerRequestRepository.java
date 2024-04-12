package com.team.saver.partner.request.repository;

import com.team.saver.partner.request.dto.PartnerRequestResponse;

import java.util.List;

public interface CustomPartnerRequestRepository {

    List<PartnerRequestResponse> findAllEntity();

}
