package com.codingrecipe.member.controller.userController;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.exception.CustomValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class LogoutController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            Map<String, Object> response = new HashMap<>();
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                jwtTokenProvider.invalidateToken(username);

                response.put("message", "로그아웃 성공");
                return ResponseEntity.ok().body(response);
            } else {
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "유효하지 않은 토큰");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e){
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "로그아웃 오류");
        }
    }
}
