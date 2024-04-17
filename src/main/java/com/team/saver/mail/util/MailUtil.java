package com.team.saver.mail.util;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public boolean sendMail(String ToEmail, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setSubject("이메일 인증 안내");
            helper.setTo(ToEmail);
            helper.setFrom("sangjinb609@gmail.com");

            String BODY =
                    "사용자 [" + ToEmail + "]님에 대한" +
                    "\n인증 코드는 다음과 같습니다" +
                    "\n[ "+ code + " ] " +
                    "\n인증 코드란에 정확히 기입해주세요." ;
            helper.setText(BODY);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
