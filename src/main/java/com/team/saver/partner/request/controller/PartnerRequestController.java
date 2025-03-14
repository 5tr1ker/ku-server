package com.team.saver.partner.request.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.common.dto.LogInNotEssential;
import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.partner.request.dto.PartnerRequestCreateRequest;
import com.team.saver.partner.request.dto.PartnerRequestDetailResponse;
import com.team.saver.partner.request.dto.PartnerRequestResponse;
import com.team.saver.partner.request.dto.PartnerRequestUpdateRequest;
import com.team.saver.partner.request.entity.PartnerRequest;
import com.team.saver.partner.request.service.PartnerRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.team.saver.common.dto.ErrorMessage.NOT_FOUND_PARTNER_REQUEST;

@RestController
@RequiredArgsConstructor
public class PartnerRequestController {

    private final PartnerRequestService partnerRequestService;

    @PostMapping("/v1/partners/requests")
    @Operation(summary = "[ 로그인 ] 새로운 파트너십 요청 API ( 80 )")
    public ResponseEntity requestNewPartner(@RequestBody PartnerRequestCreateRequest request,
                                            @Parameter(hidden = true) @LogIn CurrentUser currentUser) {
        partnerRequestService.requestNewPartner(request, currentUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/v1/partners/requests")
    @Operation(summary = "[ 비 - 로그인 ] 요청된 모든 파트너십 데이터 가져오기 ( 81 )")
    public ResponseEntity findAllEntity(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                        Pageable pageable) {
        List<PartnerRequestResponse> result = partnerRequestService.findAllEntity(currentUser, pageable);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/partners/requests/{partnerRequestId}")
    @Operation(summary = "[ 비 - 로그인 ] 파트너십 상세 데이터 가져오기 ( 82 )")
    public ResponseEntity findDetailById(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                         @PathVariable long partnerRequestId) {
        PartnerRequestDetailResponse result = partnerRequestService.findDetailById(currentUser, partnerRequestId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/partners/requests/bests")
    @Operation(summary = "[ 비 - 로그인 ] 가장 인기있는 파트너십 데이터 가져오기 ( 83 )")
    public ResponseEntity findMostRecommend(@Parameter(hidden = true) @LogInNotEssential CurrentUser currentUser,
                                            @RequestParam long size) {
        List<PartnerRequestResponse> result = partnerRequestService.findMostRecommend(currentUser, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/partners/requests/totals")
    @Operation(summary = "전체 파트너십 데이터 갯수 가져오기 ( 84 )")
    public ResponseEntity findTotalPartnerRequestCount() {
        long result = partnerRequestService.findTotalPartnerRequestCount();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/v1/partners/requests/{partnerRequestId}/recommendation")
    @Operation(summary = "[ 로그인 ] 파트너쉽 추천 API ( 85 )")
    public ResponseEntity requestPartnerRecommendation(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                       @PathVariable long partnerRequestId) {
        partnerRequestService.requestPartnerRecommendation(currentUser, partnerRequestId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/partners/requests/{partnerRequestId}/recommendation")
    @Operation(summary = "[ 로그인 ] 파트너쉽 추천 취소 API ( 86 )")
    public ResponseEntity deleteRecommendation(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @PathVariable long partnerRequestId) {
        partnerRequestService.deleteRecommendation(currentUser, partnerRequestId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/v1/partners/requests/{partnerRequestId}")
    @Operation(summary = "[ 로그인 ] 파트너쉽 수정 API ( 87 )")
    public ResponseEntity updatePartnerRequest(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @RequestBody PartnerRequestUpdateRequest request,
                                               @PathVariable long partnerRequestId) {
        partnerRequestService.updatePartnerRequest(currentUser, request, partnerRequestId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/partners/requests/{partnerRequestId}")
    @Operation(summary = "[ 로그인 ] 파트너쉽 삭제 API ( 88 )")
    public ResponseEntity deletePartnerRequest(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                               @PathVariable long partnerRequestId) {
        partnerRequestService.deletePartnerRequest(currentUser, partnerRequestId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
