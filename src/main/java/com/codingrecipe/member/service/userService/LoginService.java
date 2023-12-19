package com.codingrecipe.member.service.userService;


import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.Security.TokenStore;
import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;


    // 생성자 주입으로 BCryptPasswordEncoder 인스턴스를 받음
    public LoginService(PatientRepository patientRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.patientRepository = patientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<?> login(String userId, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userId,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwtTokenProvider.invalidateToken(userId);
        // 기존 토큰 무효화
        tokenStore.removeToken(userId);
// 토큰 제거 확인
        if (tokenStore.isTokenRemoved(userId)) {
            // 토큰이 제거되었음을 로그 또는 기타 방법으로 기록
            System.out.println("토큰 제거");
        }
        String token = jwtTokenProvider.createToken(authentication);
        tokenStore.storeToken(userId, token);
        // 헤더에 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        System.out.println("token = "+token);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.OK.value());
        responseBody.put("message", "로그인 성공");
        //responseBody.put("token", token);

        return ResponseEntity.ok().headers(headers).body(responseBody);
    }
}
