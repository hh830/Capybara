package com.codingrecipe.member;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.exception.CustomValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void whenTokenIsValid_thenShouldReturnTrue() {
        // 유효한 토큰 생성
        String token = "여기에 유효한 토큰을 입력하세요";

        // 토큰 유효성 검증
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    public void whenTokenIsInvalid_thenShouldThrowException() {
        // 유효하지 않은 토큰
        String invalidToken = "여기에 유효하지 않은 토큰을 입력하세요";

        // 토큰 유효성 검증
        assertThrows(CustomValidationException.class, () -> {
            jwtTokenProvider.validateToken(invalidToken);
        });
    }
}
