package com.project.dailylog.sign.controller;

import com.project.dailylog.sign.DTO.Reset.CheckUserDTO;
import com.project.dailylog.sign.DTO.Reset.ResetPwDTO;
import com.project.dailylog.sign.service.MailService;
import com.project.dailylog.sign.service.ResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResetController {
    private final ResetService resetService;
    private final MailService mailService;

    private long modifyTarget;

    @PostMapping("/check-user")
    public ResponseEntity<String> checkInUser(@RequestBody CheckUserDTO checkUserDTO) throws Exception {
        modifyTarget = resetService.checkInUser(checkUserDTO);
        mailService.sendMessage(checkUserDTO.getEmail());
        return ResponseEntity.ok(modifyTarget + "번 : " + checkUserDTO.getEmail());
    }

    @PostMapping("/modify-pw")
    public ResponseEntity<String> modifyPassword(@RequestBody ResetPwDTO resetPwDTO) {
        resetService.updateUser(resetPwDTO, modifyTarget);
        String email = resetPwDTO.getEmail();
        return ResponseEntity.ok(email + " 비밀번호 수정 성공");
    }
}
