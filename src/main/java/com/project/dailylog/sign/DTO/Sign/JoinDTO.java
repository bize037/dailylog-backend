package com.project.dailylog.sign.DTO.Sign;

import com.project.dailylog.sign.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    private String password;
    private String email;
    private String name;
    private String gender;
    private LocalDate birth;
    private LocalDateTime joinDate;
    private String nickname;
    private final List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .gender(gender)
                .birth(birth)
                .joinDate(LocalDateTime.now())
                .nickname(nickname)
                .roles(roles)
                .build();
    }
}
