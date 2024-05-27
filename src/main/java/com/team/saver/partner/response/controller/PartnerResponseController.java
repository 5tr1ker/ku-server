package com.team.saver.partner.response.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.partner.response.dto.NewPartnerResponse;
import com.team.saver.partner.response.service.PartnerResponseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner/response")
public class PartnerResponseController {

    private final PartnerResponseService partnerResponseService;

    @PostMapping
    @Operation(summary = "파트너 십 요청에 대한 결과 등록")
    public ResponseEntity addPartnerResponse(@LogIn CurrentUser currentUser,
                                             @RequestBody NewPartnerResponse response) {
        partnerResponseService.addPartnerResponse(currentUser, response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{responseId}")
    @Operation(summary = "파트너 십 요청에 대한 결과 삭제")
    public ResponseEntity deletePartnerResponse(@LogIn CurrentUser currentUser,
                                                @PathVariable long responseId) {
        partnerResponseService.deletePartnerResponse(currentUser, responseId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
