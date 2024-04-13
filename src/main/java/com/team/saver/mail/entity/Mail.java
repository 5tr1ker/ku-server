package com.team.saver.mail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mailCertId;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String verificationCode;

    public boolean isCorrectVerificationCode(String code) {
        return verificationCode.equals(code);
    }

    public static Mail createEntity(String id, String code) {
        return Mail.builder()
                .id(id)
                .verificationCode(code)
                .build();
    }

}
