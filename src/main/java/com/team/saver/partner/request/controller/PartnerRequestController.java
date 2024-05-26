package com.team.saver.partner.request.controller;

import com.team.saver.account.entity.Account;
import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.dto.NewPartnerRequest;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.entity.PartnerRecommender;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.service.PartnerRequestService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.EXIST_RECOMMENDER;
import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_PARTNER_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/request")
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

    @PostMapping
    @Operation(summary = "새로운 파트너십 요청 API [ 사용자 인증 정보 필요 ]")
    public ResponseEntity requestNewPartner(@RequestBody NewPartnerRequest request
            , @LogIn CurrentUser currentUser) {
        partnerRequestService.requestNewPartner(request, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(summary = "요청된 모든 파트너십 데이터 가져오기")
    public ResponseEntity findAllEntity() {
        List<PartnerRequestResponse> result = partnerRequestService.findAllEntity();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{partnerRequestId}/recommendation")
    @Operation(summary = "파트너쉽 추천 API")
    public ResponseEntity requestPartnerRecommendation(@LogIn CurrentUser currentUser,
                                                       @PathVariable long partnerRequestId) {
        partnerRequestService.requestPartnerRecommendation(currentUser, partnerRequestId);

        return ResponseEntity.ok().build();
    }

}
