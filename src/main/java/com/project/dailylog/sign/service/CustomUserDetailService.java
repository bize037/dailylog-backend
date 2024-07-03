package com.project.dailylog.sign.service;

import com.project.dailylog.common.constants.ErrorMessage;
import com.project.dailylog.sign.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.NOT_FOUND_USER.getMessage()));
    }

    public UserDetails createUserDetails(com.project.dailylog.sign.domain.User user) {
        return User.builder().username(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(user.getRoles().toArray(EMPTY_STRING_ARRAY))
                .build();
    }
}
