package com.codingrecipe.member.controller.userController;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.dto.userDTO.UserDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.userRepository.PatientRepository;
import com.codingrecipe.member.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@ControllerAdvice
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PatientRepository patientRepository; // UserRepository 추가

    @Autowired
    private UserService userService;

    @GetMapping("/users/me")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication);
        Map<String, Object> responseBody = new HashMap<>();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("UserController.getUserInfo");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 접근입니다.");
        }else {
            if (authentication.getPrincipal() instanceof UserDetails) {
                try {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    // 현재 인증된 사용자의 고유 식별자 가져오기
                    // userDetails를 사용하여 사용자 정보를 가져오거나 처리합니다.
                    String username = userDetails.getUsername();
                    UserDTO userDTO = userService.getUserInfo(username);

                    responseBody.put("status", HttpStatus.OK.value());
                    responseBody.put("message", "사용자 조회 성공");
                    responseBody.put("userId", username);
                    responseBody.put("userName", userDTO.getUserName());
                    responseBody.put("birthDate", userDTO.getBirthDate());
                    responseBody.put("phoneNumber", userDTO.getPhoneNumber());

                    return ResponseEntity.ok(responseBody);

                } catch (Exception e) {
                    throw new CustomValidationException(HttpStatus.NOT_FOUND.value(), "사용자 조회 실패");
                }

            } else {
                responseBody.put("message", "사용자 정보 없음");
                responseBody.put("status", HttpStatus.UNAUTHORIZED.value());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(responseBody);
            }
        }
    }
}


