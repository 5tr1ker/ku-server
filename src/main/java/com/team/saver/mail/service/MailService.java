package com.team.saver.mail.service;

import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.entity.Mail;
import com.team.saver.mail.repository.MailRepository;
import com.team.saver.mail.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.team.saver.common.dto.ErrorMessage.NOT_MATCHED_CODE;
import static com.team.saver.common.dto.ErrorMessage.SMTP_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MailRepository mailRepository;

    @Transactional
    public void sendMail(MailRequest request) {
        Mail mailCert = createVerification(request.getEmail());

        if (!mailUtil.sendMail(request.getEmail(), mailCert.getVerificationCode())) {
            throw new CustomRuntimeException(SMTP_SERVER_ERROR);
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
                .orElseThrow(() -> new CustomRuntimeException(NOT_MATCHED_CODE));

        if(!mailCert.isCorrectVerificationCode(request.getCode())) {
            throw new CustomRuntimeException(NOT_MATCHED_CODE);
        }

        return true;
    }

    private String createVerificationCode() {
        String code = UUID.randomUUID().toString();

        return code;
    }

}
