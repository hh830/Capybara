package com.codingrecipe.member.controller;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.dto.LoginDTO;
import com.codingrecipe.member.dto.RegistrationDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.LoginService;
import com.codingrecipe.member.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserId(),
                            loginDTO.getPassword()
                    )
            );

            String token = jwtTokenProvider.createToken(authentication);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "로그인 성공");
            responseBody.put("username", loginDTO.getUserId());
            responseBody.put("token", token);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            Map<String, Object> responseBody = new HashMap<>();
            //responseBody.put("message", "로그인 실패");
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "로그인 실패");

        }
    }

}
