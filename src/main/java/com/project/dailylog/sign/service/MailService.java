package com.project.dailylog.sign.service;

import com.project.dailylog.common.constants.Mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private static final int CREATE_MIN_RANDOM = 1000;
    private static final int CREATE_MAX_RANDOM = 10000;

    private final JavaMailSender emailSender;

    private String authPw;

    public MimeMessage createMessage(String to) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject(Message.SUBJECT.getMessage());
        message.setText(getMessage(), Message.CHARSET.getMessage(), Message.SUBTYPE.getMessage());
        message.setFrom(new InternetAddress(Message.FROM_ADDRESS.getMessage()));

        return message;
    }

    public String sendMessage(String to) throws Exception {
        authPw = createKey();
        MimeMessage message = createMessage(to);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error(e.getMessage());
        }
        return authPw;
    }

    public boolean isMatchAuthCode(String code) {
        return code.equals(authPw);
    }

    private String createKey() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(CREATE_MIN_RANDOM, CREATE_MAX_RANDOM));
    }

    private String getMessage() {
        return String.format(Message.TEXT.getMessage(), authPw);
    }
}
