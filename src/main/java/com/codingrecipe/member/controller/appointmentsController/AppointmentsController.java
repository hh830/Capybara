package com.codingrecipe.member.controller.appointmentsController;

import com.codingrecipe.member.dto.appointmentsDTO.AppointmentsDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.appointmentsService.AppointmentsService; // 올바른 서비스 클래스 import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/reservations")
public class AppointmentsController {

    @Autowired
    private AppointmentsService appointmentsService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody AppointmentsDTO appointmentsDTO) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
                // 인증된 사용자의 정보를 활용
                String userId = authentication.getName();
                validateRequest(appointmentsDTO);
                return appointmentsService.createReservation(appointmentsDTO, userId);

            } else {
                throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰");
            }


        } catch (CustomValidationException e) {
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("status", e.getStatus());
            errorDetails.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.valueOf(e.getStatus()))
                    .body(errorDetails);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    private void validateRequest(AppointmentsDTO appointmentsDTO) {
        if (!appointmentsDTO.getHospitalId().matches("\\d{3}-\\d{2}-\\d{5}")) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 형식(3-2-5)");
        }

        if (appointmentsDTO.getTime().getMinute() != 0) {
            throw new CustomValidationException(HttpStatus.BAD_REQUEST.value(), "잘못된 형식(time)");
        }
    }
}
