package com.team.saver.partner.comment.controller;

import com.team.saver.common.dto.CurrentUser;
import com.team.saver.common.dto.LogIn;
import com.team.saver.partner.comment.dto.PartnerCommentCreateRequest;
import com.team.saver.partner.comment.service.PartnerCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PartnerCommentController {

    private final PartnerCommentService partnerCommentService;

    @PostMapping("/v1/partners/requests/{partnerRequestId}/comments")
    @Operation(summary = "[ 로그인 ] 파트너 십 요청에 대한 응답 남기기")
    public ResponseEntity addPartnerResponse(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                             @RequestBody PartnerCommentCreateRequest response,
                                             @PathVariable long partnerRequestId) {
        partnerCommentService.addPartnerResponse(currentUser ,partnerRequestId, response);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/v1/partners/requests/comments/{commentId}")
    @Operation(summary = "[ 로그인 ] 파트너 십 요청에 대한 댓글 삭제")
    public ResponseEntity deletePartnerResponse(@Parameter(hidden = true) @LogIn CurrentUser currentUser,
                                                @PathVariable long commentId) {
        partnerCommentService.deletePartnerResponse(currentUser, commentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
