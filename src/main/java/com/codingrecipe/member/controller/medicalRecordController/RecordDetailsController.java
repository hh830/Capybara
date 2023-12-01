package com.codingrecipe.member.controller.medicalRecordController;

import com.codingrecipe.member.dto.hospitalDTO.HospitalDetailsDTO;
import com.codingrecipe.member.dto.medicalRecordDTO.RecordDTO;
import com.codingrecipe.member.dto.medicalRecordDTO.RecordDetailsDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.medicalRecordService.RecordDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/records/details")
public class RecordDetailsController {

    @Autowired
    private RecordDetailsService recordDetailsService;


    @GetMapping("/{recordId}")
    public ResponseEntity<?> getRecordDetails(@PathVariable int recordId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
                // 인증된 사용자의 정보를 활용
                String userId = authentication.getName();
                RecordDetailsDTO recordDetailsDTOS = recordDetailsService.recordDetails(userId, recordId);

                return ResponseEntity.ok().body(recordDetailsDTOS);

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
}
