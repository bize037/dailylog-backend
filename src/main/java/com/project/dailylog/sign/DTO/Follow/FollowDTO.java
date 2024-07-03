package com.project.dailylog.sign.DTO.Follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FollowDTO {
    private String followerEmail;
    private String followedEmail;
}
