package com.team.saver.mail.service;

import com.team.saver.common.exception.CustomRuntimeException;
import com.team.saver.mail.dto.MailRequest;
import com.team.saver.mail.entity.Mail;
import com.team.saver.mail.repository.MailRepository;
import com.team.saver.mail.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.team.saver.common.dto.ErrorMessage.NOT_MATCHED_CODE;
import static com.team.saver.common.dto.ErrorMessage.SMTP_SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MailRepository mailRepository;

    @Transactional
    public void sendMail(String email) {
        Mail mailCert = createVerification(email);

        if (!mailUtil.sendMail(email, mailCert.getVerificationCode())) {
            throw new CustomRuntimeException(SMTP_SERVER_ERROR);
        }
    }

    private Mail createVerification(String email) {
        String code = createVerificationCode();

        Mail mailCert = createVerificationCode(email, code);

        return mailRepository.save(mailCert);
    }

    private Mail createVerificationCode(String id, String code) {
        Mail mailCert = mailRepository.findById(id)
                .orElseGet(() -> Mail.createEntity(id, code));

        return mailCert;
    }

    @Transactional
    public void checkVerificationCode(MailRequest request) {
        if(isCorrectVerificationCode(request)) {
            mailRepository.deleteById(request.getSchoolEmail());
        }
    }

    private boolean isCorrectVerificationCode(MailRequest request) {
        Mail mailCert = mailRepository.findById(request.getSchoolEmail())
                .orElseThrow(() -> new CustomRuntimeException(NOT_MATCHED_CODE));

        if(!mailCert.isCorrectVerificationCode(request.getCode())) {
            throw new CustomRuntimeException(NOT_MATCHED_CODE);
        }

        return true;
    }

    private String createVerificationCode() {
        Random random = new Random();

        int randomNumber = 100000 + random.nextInt(888888);
        return String.valueOf(randomNumber);
    }

}
