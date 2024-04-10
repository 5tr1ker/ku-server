package com.team.saver.mail.repository;

import com.team.saver.mail.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail, Long> {

    Optional<Mail> findById(String id);

    long deleteById(String id);

}