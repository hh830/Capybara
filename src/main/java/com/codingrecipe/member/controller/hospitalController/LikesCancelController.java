package com.codingrecipe.member.controller.hospitalController;

import com.codingrecipe.member.dto.hospitalDTO.LikesDTO;
import com.codingrecipe.member.exception.CustomValidationException;
import com.codingrecipe.member.service.hospitalService.LikesCancelService;
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
@RequestMapping("/likes/cancel")
public class LikesCancelController {

    @Autowired
    private LikesCancelService likesCancelService;

    @DeleteMapping("/{businessId}")
    public ResponseEntity<?> cancelLikes(@PathVariable String businessId){
        Map<String, Object> response = new HashMap<>();
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {
                // 인증된 사용자의 정보를 활용
                String userId = authentication.getName();
                return likesCancelService.deleteLike(businessId, userId);

            } else {
                throw new CustomValidationException(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰");
            }

        } catch (CustomValidationException e){
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "잘못된 요청");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e){
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
