package com.project.dailylog.common.constants;

import lombok.Getter;

public class Mail {
    @Getter
    public enum Message {
        FROM_ADDRESS("dailylog2024@naver.com"),
        SUBJECT("[DAILYLOG] 데일리로그 인증번호입니다."),
        TEXT("데일리로그 인증번호는 <strong style=\"color:#746AB0;\">%s</strong>입니다."),
        CHARSET("utf-8"),
        SUBTYPE("html");

        private final String message;

        Message(String message) {
            this.message = message;
        }

    }

    @Getter
    public enum Property {
        MAIL_PROTOCOL("mail.transport.protocol"),
        SMTP_AUTH("mail.smtp.auth"),
        STARTTLS_ENABLE("mail.smtp.starttls.enable"),
        DEBUG("mail.debug"),
        SMTP_SSL_TRUST("mail.smtp.ssl.trust"),
        SMTP_SSL_ENABLE("mail.smtp.ssl.enable");

        private final String key;

        Property(String key) {
            this.key = key;
        }

    }
}

