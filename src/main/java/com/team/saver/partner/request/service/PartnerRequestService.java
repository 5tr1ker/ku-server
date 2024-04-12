package com.team.saver.partner.request.service;

import com.team.saver.partner.request.repository.PartnerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerRequestService {

    private final PartnerRequestRepository partnerRequestRepository;

}
