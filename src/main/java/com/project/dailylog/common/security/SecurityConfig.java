package com.project.dailylog.common.security;

import com.project.dailylog.common.constants.Authorization;
import com.project.dailylog.common.token.JwtAuthenticationFilter;
import com.project.dailylog.common.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf(csrf->csrf.disable())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/join").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout-user").permitAll()
                .requestMatchers("/auth-email").permitAll()
                .requestMatchers("/auth-email-confirm").permitAll()
                .requestMatchers("/check-user").permitAll()
                .requestMatchers("/modify-pw").permitAll()
                .requestMatchers("/oauth2/authorize/kakao").permitAll()
                .requestMatchers("/board/**").hasRole(Authorization.AUTHORIZE_USER.getAuth())
                .requestMatchers("/api/**").hasRole(Authorization.AUTHORIZE_USER.getAuth())
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
