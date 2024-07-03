package com.project.dailylog.sign.service;

import com.project.dailylog.sign.DTO.Reset.CheckUserDTO;
import com.project.dailylog.sign.DTO.Reset.ResetPwDTO;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResetService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public long checkInUser(CheckUserDTO checkUserDTO) {
        Optional<User> existUser = userRepository.findByNameAndEmail(checkUserDTO.getName(), checkUserDTO.getEmail());
        return existUser.get().getUserId();
    }

    @Transactional
    public ResetPwDTO updateUser(ResetPwDTO resetPwDTO, long userId) {
        userRepository.updatePasswordByUserId(resetPwDTO.getPassword(), resetPwDTO.getEmail(), resetPwDTO.getName(), userId);
        return null;
    }
}
