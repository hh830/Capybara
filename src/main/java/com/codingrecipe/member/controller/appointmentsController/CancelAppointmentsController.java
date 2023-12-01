package com.codingrecipe.member.controller.appointmentsController;

import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.appointmentsService.CancelAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservations/cancel")
public class CancelAppointmentsController {
    @Autowired
    private CancelAppointmentsService cancelAppointmentsService;

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> CancelReservations(@PathVariable int appointmentId)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Map<String, Object> response = new HashMap<>();
            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
                // 인증된 사용자의 정보를 활용
                String userId = authentication.getName();

                boolean isCancelled = cancelAppointmentsService.cancelAppointment(appointmentId, userId);
                if (isCancelled) {
                    response.put("status", HttpStatus.OK.value());
                    response.put("appointmentId", appointmentId);
                    response.put("message", "예약 취소 완료");

                    return ResponseEntity.ok().body(response);
                } else {
                    response.put("status", HttpStatus.BAD_REQUEST.value());
                    response.put("appointmentId", appointmentId);
                    response.put("message", "예약 취소 실패");

                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "유효하지 않은 토큰");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            throw new CustomValidationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }
}
