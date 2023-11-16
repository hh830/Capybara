package com.codingrecipe.member.controller;

import com.codingrecipe.member.Security.CustomUserDetails;
import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.dto.ProfileDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.PatientRepository;
import com.codingrecipe.member.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@ControllerAdvice
public class ProfileController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PatientRepository patientRepository; // UserRepository 추가

    @Autowired
    private ProfileService profileService;

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUserProfile(@RequestBody ProfileDTO profileDTO) {
        // 현재 인증된 사용자의 고유 식별자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = extractUserId(authentication);
        System.out.println("userId = " + userId);
        // 사용자 정보 수정 로직
        try {
            profileService.updateProfile(userId, profileDTO);
            Optional<Patients> patientsOptional = patientRepository.findById(userId);

            //String phoneNumber = null;
            //if (patientsOptional.isPresent()) {
            //Patients patients = patientsOptional.get();
            //phoneNumber = patients.getPhoneNumber();
                // phoneNumber를 사용하여 처리


            return createResponse(profileDTO.getUserName(), profileDTO.getPhoneNumber(), "사용자 정보가 수정되었습니다.");
        } catch (Exception e) {
            // 오류 처리 (예: 사용자를 찾을 수 없음)
            System.out.println("profileDTO = " + profileDTO);
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "사용자 정보 업데이트 실패");
        }
    }


    private ResponseEntity<?> createResponse(String username, String phoneNumber, String message) {
        Map<String, Object> responseBody = new HashMap<>();
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
                // 사용자 핸드폰 번호를 어떻게 가져올지 결정하세요.
                String phoneNumber = "사용자의_핸드폰_번호";
                String message = "원하는_메세지";

                // 사용자 ID를 문자열로 반환
                return username;
            }
        } catch (Exception e) {
            throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "사용자 인증 실패");
        }
        return "왜 안될까요";
    }
}
