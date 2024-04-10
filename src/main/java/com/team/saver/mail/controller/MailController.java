package com.team.saver.mail.controller;

import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/send")
    @Operation(summary = "인증 코드가 담긴 이메일 전송 ( email만 적어 보내주세요. )")
    public ResponseEntity sendMail(@RequestBody MailRequest request) {
        mailService.sendMail(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mail/check")
    @Operation(summary = "인증 코드드 확인")
    public ResponseEntity checkMail(@RequestBody MailRequest request) {
        mailService.checkVerificationCode(request);

        return ResponseEntity.ok().build();
    }

}
