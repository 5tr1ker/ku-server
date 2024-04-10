package com.team.saver.mail.service;

import com.team.saver.account.entity.Account;
import com.team.saver.account.repository.AccountRepository;
import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.entity.Mail;
import com.team.saver.mail.repository.MailRepository;
import com.team.saver.mail.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.team.saver.common.dto.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MailRepository mailRepository;

    @Transactional
    public void sendMail(MailRequest request) {
        Mail mailCert = createVerification(request.getEmail());

        if (!mailUtil.sendMail(request.getEmail(), mailCert.getVerificationCode())) {
            throw new RuntimeException(SMTP_SERVER_ERROR.getMessage());
        }
    }

    private Mail createVerification(String id) {
        String code = createVerificationCode();

        Mail mailCert = createVerificationCode(id, code);

        return mailRepository.save(mailCert);
    }

    private Mail createVerificationCode(String id, String code) {
        Mail mailCert = mailRepository.findById(id)
                .orElseGet(() -> Mail.createMailCert(id, code));

        return mailCert;
    }

    @Transactional
    public void checkVerificationCode(MailRequest request) {
        if(isCorrectVerificationCode(request)) {
            mailRepository.deleteById(request.getEmail());
        }
    }

    private boolean isCorrectVerificationCode(MailRequest request) {
        Mail mailCert = mailRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException(NOT_MATCHED_CODE.getMessage()));

        if(!mailCert.isCorrectVerificationCode(request.getCode())) {
            throw new RuntimeException(NOT_MATCHED_CODE.getMessage());
        }

        return true;
    }

    private String createVerificationCode() {
        String code = UUID.randomUUID().toString();

        return code;
    }

}
