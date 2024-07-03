package com.project.dailylog.sign.controller;

import com.project.dailylog.sign.DTO.Follow.FollowDTO;
import com.project.dailylog.sign.service.FollowService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody FollowDTO followDTO) {
        followService.follow(followDTO.getFollowerEmail(), followDTO.getFollowedEmail());
        return ResponseEntity.ok("팔로우");
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody FollowDTO followDTO) {
        followService.unfollow(followDTO.getFollowerEmail(), followDTO.getFollowedEmail());
        return ResponseEntity.ok("언팔로우");
    }

    @GetMapping("/followers")
    public ResponseEntity<List<String>> getFollowers(@RequestBody FollowDTO followDTO) {
        List<String> followers = followService.getFollowers(followDTO.getFollowerEmail());
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings")
    public ResponseEntity<List<String>> getFollowings(@RequestBody FollowDTO followDTO) {
        List<String> followings = followService.getFollowings(followDTO.getFollowedEmail());
        return ResponseEntity.ok(followings);
    }

    @GetMapping("/isFollowing")
    public ResponseEntity<Boolean> isFollowing(@RequestBody FollowDTO followDTO) {
        boolean isFollowing = followService.isFollowing(followDTO.getFollowerEmail(), followDTO.getFollowedEmail());
        return ResponseEntity.ok(isFollowing);
    }
}
