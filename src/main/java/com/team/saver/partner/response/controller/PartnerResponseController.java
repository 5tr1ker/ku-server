package com.team.saver.partner.response.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.partner.response.dto.NewPartnerResponse;
import com.team.saver.partner.response.service.PartnerResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PartnerResponseController {

    private final PartnerResponseService partnerResponseService;

    @PostMapping("/v1/partners/requests/{partnerRequestId}/responses")
    @Operation(summary = "[ 로그인 ] 파트너 십 요청에 대한 응답 남기기")
    public ResponseEntity addPartnerResponse(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                             @RequestBody NewPartnerResponse response,
                                             @PathVariable long partnerRequestId) {
        partnerResponseService.addPartnerResponse(currentUser ,partnerRequestId, response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/partners/requests/responses/{responseId}")
    @Operation(summary = "[ 로그인 ] 파트너 십 요청에 대한 댓글 삭제")
    public ResponseEntity deletePartnerResponse(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                @PathVariable long responseId) {
        partnerResponseService.deletePartnerResponse(currentUser, responseId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
