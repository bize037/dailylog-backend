package com.project.dailylog.sign.service;

import com.project.dailylog.common.constants.ErrorMessage;
import com.project.dailylog.sign.domain.Follow;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.FollowRepository;
import com.project.dailylog.sign.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(String followerEmail, String followedEmail) {
        User follower = userRepository.findById(getFollowId(followerEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));
        User followed = userRepository.findById(getFollowId(followedEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        Follow follow = Follow.builder()
                .follower(follower)
                .followed(followed)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unfollow(String followerEmail, String followedEmail) {
        User follower = userRepository.findById(getFollowId(followerEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));
        User followed = userRepository.findById(getFollowId(followedEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    @Transactional(readOnly = true)
    public List<String> getFollowers(String email) {
        User user = userRepository.findById(getFollowId(email))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        List<Follow> follows = followRepository.findByFollowed(user);
        return follows.stream()
                .map(follow -> getFollowEmail(follow.getFollower().getUserId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> getFollowings(String email) {
        User user = userRepository.findById(getFollowId(email))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        List<Follow> follows = followRepository.findByFollower(user);
        return follows.stream()
                .map(follow -> getFollowEmail(follow.getFollowed().getUserId()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean isFollowing(String followerEmail, String followedEmail) {
        User follower = userRepository.findById(getFollowId(followerEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));
        User followed = userRepository.findById(getFollowId(followedEmail))
                .orElseThrow(() -> new RuntimeException(ErrorMessage.NOT_FOUND_USER.getMessage()));

        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed);
        return follow != null;
    }

    private Long getFollowId(String followerEmail) {
        Optional<User> user = userRepository.findByEmail(followerEmail);
        return user.get().getUserId();
    }

    private String getFollowEmail(Long followerId) {
        Optional<User> user = userRepository.findById(followerId);
        return user.get().getEmail();
    }
}
