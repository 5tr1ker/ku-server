package com.team.saver.partner.request.controller;

import com.team.saver.account.entity.Account;
import com.team.saver.partner.request.dto.NewPartnerRequest;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.service.PartnerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/request")
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

    @PostMapping
    public ResponseEntity requestNewPartner(@RequestBody NewPartnerRequest request
            , @AuthenticationPrincipal UserDetails userDetails) {
        partnerRequestService.requestNewPartner(request, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity findAllEntity() {
        List<PartnerRequestResponse> result = partnerRequestService.findAllEntity();

        return ResponseEntity.ok(result);
    }

}
