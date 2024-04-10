package com.team.saver.mail.controller;

import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail/send")
    public ResponseEntity sendMail(@RequestBody MailRequest request) {
        mailService.sendMail(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/mail/check")
    public ResponseEntity checkMail(@RequestBody MailRequest request) {
        mailService.checkVerificationCode(request);

        return ResponseEntity.ok().build();
    }

}
