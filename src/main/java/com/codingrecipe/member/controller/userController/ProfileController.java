package com.codingrecipe.member.controller.userController;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.dto.userDTO.ProfileDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.PatientRepository;
import com.codingrecipe.member.service.userService.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PatientRepository patientRepository; // UserRepository 추가
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private ProfileService profileService;

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileDTO profileDTO) {
        Map<String, Object> errorDetails = new HashMap<>();


        // 현재 인증된 사용자의 고유 식별자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 접근입니다.");
        }
        else {
            String userId = extractUserId(authentication);
            System.out.println("userId = " + userId);
            // 사용자 정보 수정 로직
            try {
                profileService.updateProfile(userId, profileDTO);

                return createResponse(HttpStatus.OK.value(), profileDTO.getUserName(), profileDTO.getPhoneNumber(), "사용자 정보 수정 완료");
            } catch (CustomValidationException e) {

                errorDetails.put("status", e.getStatus());
                errorDetails.put("message", e.getMessage());
                return ResponseEntity
                        .status(HttpStatus.valueOf(e.getStatus()))
                        .body(errorDetails);
            }
        }
    }


    private ResponseEntity<?> createResponse(int HttpStatus, String username, String phoneNumber, String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus);
        responseBody.put("username", username);
        responseBody.put("phoneNumber", phoneNumber);
        responseBody.put("message", message);

        return ResponseEntity.ok(responseBody);
    }

    private String extractUserId(Authentication authentication) {
        try {
            Object principal = authentication.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
                String username = user.getUsername();

                // 사용자 ID를 문자열로 반환
                return username;
            }
        } catch (Exception e) {
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "사용자 인증 실패");
        }
        return "왜 안될까요";
    }
}
