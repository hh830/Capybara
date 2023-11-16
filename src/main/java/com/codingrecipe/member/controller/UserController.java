package com.codingrecipe.member.controller;

import com.codingrecipe.member.Security.JwtTokenProvider;
import com.codingrecipe.member.dto.UserDTO;
import com.codingrecipe.member.entity.Patients;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.repository.PatientRepository;
import com.codingrecipe.member.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@ControllerAdvice
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PatientRepository patientRepository; // UserRepository 추가

    @Autowired
    private ProfileService UsersService;
    @GetMapping("/users")
    public ResponseEntity<UserDTO> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String userId = extractUserId(authentication);

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // 현재 인증된 사용자의 고유 식별자 가져오기
            // userDetails를 사용하여 사용자 정보를 가져오거나 처리합니다.
            String username = userDetails.getUsername();

            Optional<Patients> patientsOptional = patientRepository.findById(username);

            UserDTO userDTO = new UserDTO(username, );
            return ResponseEntity.ok(userDTO);

        } else {
            // 사용자 정보가 없는 경우에 대한 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

}
