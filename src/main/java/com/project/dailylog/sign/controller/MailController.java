package com.project.dailylog.sign.controller;

import com.project.dailylog.sign.DTO.Mail.AuthCodeDTO;
import com.project.dailylog.sign.DTO.Mail.EmailPostDTO;
import com.project.dailylog.sign.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/auth-email")
    public ResponseEntity<String> authMail(@RequestBody EmailPostDTO emailPostDTO) throws Exception {
        String code = mailService.sendMessage(emailPostDTO.getEmail());
        return ResponseEntity.ok(code);
    }

    @PostMapping("/auth-email-confirm")
    public ResponseEntity<Boolean> authMailConfirm(@RequestBody AuthCodeDTO authCodeDTO) {
        boolean match = mailService.isMatchAuthCode(authCodeDTO.getCode());
        if (match) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
    }
}
