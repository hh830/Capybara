package com.codingrecipe.member.controller.userController;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.Security.TokenStore;
import com.codingrecipe.member.dto.userDTO.LoginDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.userService.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

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

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            return loginService.login(loginDTO.getUserId(), loginDTO.getPassword());
        }
        catch (Exception e) {
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "로그인 실패");
        }
    }
}
