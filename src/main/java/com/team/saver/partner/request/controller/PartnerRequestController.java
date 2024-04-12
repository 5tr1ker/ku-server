package com.team.saver.partner.request.controller;

import com.team.saver.partner.request.service.PartnerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/request")
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

}
