package com.team.saver.partner.request.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.partner.request.dto.PartnerRequestCreateRequest;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.service.PartnerRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

    @PostMapping("/v1/partners/requests")
    @Operation(summary = "[ 로그인 ] 새로운 파트너십 요청 API")
    public ResponseEntity requestNewPartner(@RequestBody PartnerRequestCreateRequest request
            , @Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        partnerRequestService.requestNewPartner(request, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/partners/requests")
    @Operation(summary = "요청된 모든 파트너십 데이터 가져오기")
    public ResponseEntity findAllEntity(Pageable pageable) {
        List<PartnerRequestResponse> result = partnerRequestService.findAllEntity(pageable);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/partners/requests/{partnerRequestId}/recommendation")
    @Operation(summary = "[ 로그인 ] 파트너쉽 추천 API")
    public ResponseEntity requestPartnerRecommendation(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                       @PathVariable long partnerRequestId) {
        partnerRequestService.requestPartnerRecommendation(currentUser, partnerRequestId);

        return ResponseEntity.ok().build();
    }

}
