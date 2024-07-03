package com.project.dailylog.sign.service;

import com.project.dailylog.common.constants.Authorization;
import com.project.dailylog.common.constants.ErrorMessage;
import com.project.dailylog.common.token.JwtToken;
import com.project.dailylog.common.token.JwtTokenProvider;
import com.project.dailylog.sign.DTO.Sign.JoinDTO;
import com.project.dailylog.sign.DTO.Sign.UserDTO;
import com.project.dailylog.sign.domain.User;
import com.project.dailylog.sign.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO signUp(JoinDTO joinDTO) {
        if (userRepository.existsByEmail(joinDTO.getEmail())) {
            throw new IllegalArgumentException(ErrorMessage.ALREADY_USED_EMAIL.getMessage());
        }
        String encodedPassword = passwordEncoder.encode(joinDTO.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add(Authorization.AUTHORIZE_USER.getAuth());
        return UserDTO.toDto(userRepository.save(joinDTO.toEntity(encodedPassword, roles)));
    }

    @Transactional(readOnly = true)
    public JwtToken signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_EMAIL.getMessage());
        }
        User user = userOptional.get();

        if (!authenticate(password, user.getPassword())) {
            throw new IllegalArgumentException(user.getPassword());
        }

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, user.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional(readOnly = true)
    public UserDTO findUserProfile(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return UserDTO.toDto(user.get());
    }

    public boolean authenticate(String userInputPassword, String storedHashedPassword) {
        return passwordEncoder.matches(userInputPassword, storedHashedPassword);
    }
}
