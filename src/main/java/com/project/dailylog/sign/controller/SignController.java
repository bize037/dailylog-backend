package com.project.dailylog.sign.controller;

import com.project.dailylog.sign.DTO.Sign.JoinDTO;
import com.project.dailylog.sign.DTO.Sign.LoginDTO;
import com.project.dailylog.sign.DTO.Sign.TokenDTO;
import com.project.dailylog.sign.DTO.Sign.UserDTO;
import com.project.dailylog.sign.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

    @PostMapping("/join")
    public ResponseEntity<UserDTO> signUp(@RequestBody JoinDTO joinDTO) {
        UserDTO savedUserDto = signService.signUp(joinDTO);
        return ResponseEntity.ok(savedUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String token = signService.signIn(loginDTO.getEmail(), loginDTO.getPassword()).getAccessToken();
        return ResponseEntity.ok(new TokenDTO(token, loginDTO.getEmail()));
    }

    @PostMapping("/logout-user")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDTO userDTO = signService.findUserProfile(email);
        return ResponseEntity.ok(userDTO);
    }
}
