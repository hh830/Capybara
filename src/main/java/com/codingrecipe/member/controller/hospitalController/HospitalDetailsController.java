package com.codingrecipe.member.controller.hospitalController;

import com.codingrecipe.member.dto.hospitalDTO.HospitalDetailsDTO;
import com.codingrecipe.member.service.hospitalService.HospitalDetailsService;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/hospitals/details")
public class HospitalDetailsController {

    @Autowired
    private HospitalDetailsService hospitalDetailsService;

    @GetMapping("/{hospitalId}")
    public ResponseEntity<?> getHospitalDetails(@PathVariable String hospitalId) {
        try {
            HospitalDetailsDTO hospitalDetailsDTO;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
                // 인증된 사용자의 정보를 활용
                String userId = authentication.getName();
                if(userId != null && !userId.isEmpty()) {
                    hospitalDetailsDTO = hospitalDetailsService.getHospitalDetails(userId, hospitalId);

                } else{
                    userId = null;
                    hospitalDetailsDTO = hospitalDetailsService.getHospitalDetails(userId, hospitalId);
                }
            }
            else{
                String userId = null;
                hospitalDetailsDTO = hospitalDetailsService.getHospitalDetails(userId, hospitalId);
            }
            return ResponseEntity.ok(hospitalDetailsDTO);
        } catch (Exception e) {
            // 예외 처리 로직 (에러 메시지 반환 등)
            Map<String,Object> errorbody = new HashMap<>();
            errorbody.put("hospitalId", hospitalId);
            errorbody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorbody.put("message", "찾을 수 없습니다");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorbody);
        }
    }
}
