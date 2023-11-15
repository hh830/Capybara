package com.codingrecipe.member.Security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private final AuthenticationManager authenticationManager;

    // AuthenticationManager를 주입받는 생성자
    public JwtConfig(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean(name = "customJwtTokenProvider")
    public JwtTokenProvider jwtTokenProvider() {
        // 수정된 부분: JwtTokenProvider 생성자에 AuthenticationManager를 넘겨줌
        return new JwtTokenProvider(secretKey, expiration);
    }
}
