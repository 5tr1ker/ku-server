package com.team.saver.partner.request.controller;

import com.team.saver.partner.request.dto.NewPartnerRequest;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.service.PartnerRequestService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "새로운 파트너십 요청 API [ 사용자 인증 정보 필요 ]")
    public ResponseEntity requestNewPartner(@RequestBody NewPartnerRequest request
            , @AuthenticationPrincipal UserDetails userDetails) {
        partnerRequestService.requestNewPartner(request, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "요청된 모든 파트너십 데이터 가져오기")
    public ResponseEntity findAllEntity() {
        List<PartnerRequestResponse> result = partnerRequestService.findAllEntity();

        return ResponseEntity.ok(result);
    }

}
