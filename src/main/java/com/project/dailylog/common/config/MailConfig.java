package com.project.dailylog.common.config;

import com.project.dailylog.common.constants.Mail.Property;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    private static final String TRUE = "true";
    private static final String SMTP_HOST = "smtp.naver.com";
    private static final int SMTP_POST = 465;

    @Bean
    public JavaMailSender javaMailSender() {
        String mailUsername = "";
        String mailPassword = "";

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(SMTP_HOST);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setPort(SMTP_POST);
        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();

        properties.setProperty(Property.MAIL_PROTOCOL.getKey(), "smtp");
        properties.setProperty(Property.SMTP_AUTH.getKey(), TRUE);
        properties.setProperty(Property.STARTTLS_ENABLE.getKey(), TRUE);
        properties.setProperty(Property.DEBUG.getKey(), TRUE);
        properties.setProperty(Property.SMTP_SSL_TRUST.getKey(), SMTP_HOST);
        properties.setProperty(Property.SMTP_SSL_ENABLE.getKey(), TRUE);

        return properties;
    }
}
