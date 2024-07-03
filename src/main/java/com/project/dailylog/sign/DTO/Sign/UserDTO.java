package com.project.dailylog.sign.DTO.Sign;

import com.project.dailylog.sign.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO { // 가입이 성공됐는지 서버에서 확인하는 DTO
    private long userId;
    private String email;
    private String name;
    private String gender;
    private LocalDate birth;
    private LocalDateTime joinDate;
    private String nickname;

    public static UserDTO toDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .birth(user.getBirth())
                .joinDate(user.getJoinDate())
                .nickname(user.getNickname())
                .build();
    }
}
